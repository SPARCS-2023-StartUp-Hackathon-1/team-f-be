package com.sparcs.teamf.api.question.service;

import com.sparcs.teamf.api.answer.exception.QuestionNotFoundException;
import com.sparcs.teamf.api.question.dto.TailQuestionResponse;
import com.sparcs.teamf.common.util.Repeat;
import com.sparcs.teamf.domain.question.Question;
import com.sparcs.teamf.domain.question.QuestionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TailQuestionService {

    private final QuestionRepository questionRepository;

    public TailQuestionResponse getTailQuestion(long questionId) {
        Question question = findQuestionById(questionId).orElseThrow(QuestionNotFoundException::new);

        if (question.getAnswer().isBlank()) {
            Repeat.repeat(() -> findQuestionById(questionId), )
        }

        if (target)
    }

    private Optional<Question> findQuestionById(long questionId) {
        return questionRepository.findById(questionId);
    }

    private boolean needToRepeat(Optional<Question> question) {
        if (question.isEmpty()) {
            return true;
        }
        return question.get().getAnswer() == null;
    }
}
