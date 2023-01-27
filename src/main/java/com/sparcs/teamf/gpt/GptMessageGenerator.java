package com.sparcs.teamf.gpt;

import static com.sparcs.teamf.gpt.constant.GptMessageConstants.FOR_ANSWER_FORMAT;
import static com.sparcs.teamf.gpt.constant.GptMessageConstants.FOR_BASIC_QUESTION_FORMAT;
import static com.sparcs.teamf.gpt.constant.GptMessageConstants.FOR_TAIL_QUESTION_FORMAT;

import com.sparcs.teamf.domain.midcategory.MidCategory;
import com.sparcs.teamf.domain.question.Question;
import org.springframework.stereotype.Component;

@Component
public class GptMessageGenerator {

    public String generateForBasicQuestion(MidCategory midCategory) {
        return String.format(FOR_BASIC_QUESTION_FORMAT, midCategory.getName());
    }

    public String generateForTailQuestion(Question question) {
        return String.format(FOR_TAIL_QUESTION_FORMAT, question.getMidCategory().getName(), question.getQuestion());
    }

    public String generateForAnswer(Question question) {
        return String.format(FOR_ANSWER_FORMAT, question.getQuestion());
    }
}

