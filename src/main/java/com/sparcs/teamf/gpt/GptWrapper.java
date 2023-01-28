package com.sparcs.teamf.gpt;

import com.sparcs.teamf.domain.gpt.Gpt;
import com.sparcs.teamf.domain.question.Question;
import com.sparcs.teamf.domain.question.QuestionRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GptWrapper implements Gpt {

    private static final String ANSWER_FORMAT = "%s 카테고리 안에 %s에 대한 질문 %s의 답을 알려 주세요.";
    private static final String NEXT_QUESTION_FORMAT =
            "%s 카테고리 안에 %s 에 대한 질문 %s 에 %s 라는 답을 했을 때 생길 수 있는 다음 질문을 하나 알려주세요";

    private final GptPool gptPool;
    private final QuestionRepository questionRepository;


    @Override
    public String ask(String question) {
        return gptPool.ask(question);
    }

    @Override
    @Async
    @Transactional
    public void loadNextQuestion(Question question) {
        List<Question> nextQuestionList = questionRepository.findQuestionByParentQuestionId(question.getId());
        if (nextQuestionList.isEmpty()) {
            throw new IllegalStateException("존재하면 안되는 상태");
        }
        Question nextQuestion = nextQuestionList.get(0);

        if (nextQuestion.getAnswer() != null) {
            return;
        }
        String answer = generateAnswer(nextQuestion);
        String nextOfNextQuestion = generateNextQuestion(question, answer);
        Question generated = new Question(nextOfNextQuestion, question.getMidCategory());
        nextQuestion.updateAnswer(answer);
        generated.updateParentQuestionId(nextQuestion.getId());
        Question saved = questionRepository.save(generated);
    }

    private String generateAnswer(Question question) {
        String mainCategoryName = question.getMidCategory().getMainCategory().getName();
        String midCategoryName = question.getMidCategory().getName();

        return gptPool.ask(generatePromptForAnswer(mainCategoryName, midCategoryName, question.getQuestion()));
    }

    private String generatePromptForAnswer(String mainCategory, String midCategory, String question) {
        return String.format(ANSWER_FORMAT, mainCategory, midCategory, question);
    }

    private String generateNextQuestion(Question question, String answer) {
        String mainCategoryName = question.getMidCategory().getMainCategory().getName();
        String midCategoryName = question.getMidCategory().getName();

        String nextQuestionPrompt = generatePromptForNextQuestion(mainCategoryName,
                midCategoryName,
                question.getQuestion(),
                answer);
        return gptPool.ask(nextQuestionPrompt);
    }

    private String generatePromptForNextQuestion(String mainCategory, String midCategory, String question,
            String answer) {
        return String.format(NEXT_QUESTION_FORMAT,
                mainCategory,
                midCategory,
                question,
                answer);
    }
}
