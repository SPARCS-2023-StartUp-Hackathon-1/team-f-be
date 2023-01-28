package com.sparcs.teamf.api.answer.service;

import com.sparcs.teamf.api.answer.exception.AnswerNotFoundException;
import com.sparcs.teamf.common.util.Repeat;
import com.sparcs.teamf.domain.question.Question;
import com.sparcs.teamf.domain.question.QuestionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionRepository questionRepository;

    public String getAnswer(long questionId) throws InterruptedException {
        Optional<Question> question = Repeat.repeat(() -> getAnswerFromRepositoryById(questionId),
                this::needToRepeat,
                AnswerNotFoundException::new);
        if (question.isEmpty()) {
            //존재할 수는 없는 케이스. 컴파일러를 위한 코드
            throw new AnswerNotFoundException();
        }
        return question.get().getAnswer();
    }

    private Optional<Question> getAnswerFromRepositoryById(long questionId) {
        return questionRepository.findById(questionId);
    }

    private boolean needToRepeat(Optional<Question> question) {
        if (question.isEmpty()) {
            return true;
        }
        if (question.get().getAnswer() == null) {
            return true;
        }
        return false;
    }
}
