package com.sparcs.teamf.api.answer.controller;

import com.sparcs.teamf.api.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/questions")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("{questionId}/answers")
    public String getAnswers(@PathVariable("questionId") String questionId) throws InterruptedException {

        return answerService.getAnswer(Long.parseLong(questionId));
    }
}
