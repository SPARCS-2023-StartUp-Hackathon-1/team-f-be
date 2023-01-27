package com.sparcs.teamf.api.gpt.service;

import com.sparcs.teamf.domain.midcategory.MidCategory;
import com.sparcs.teamf.domain.question.Question;
import com.sparcs.teamf.gpt.GptMessageGenerator;
import com.sparcs.teamf.gpt.GptPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptService {

    private final GptMessageGenerator gptMessageGenerator;
    private final GptPool gptPool;

    public String askBasicQuestion(MidCategory midCategory) {
        String message = gptMessageGenerator.generateForBasicQuestion(midCategory);
        return gptPool.ask(message);
    }

    public String askTailQuestion(Question question) {
        String message = gptMessageGenerator.generateForTailQuestion(question);
        return gptPool.ask(message);
    }

    public String askAnswer(Question question) {
        String message = gptMessageGenerator.generateForAnswer(question);
        return gptPool.ask(message);
    }
}

