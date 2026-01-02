package com.back.sbb.answer;

import java.security.Principal;


import com.back.sbb.answer.dto.AnswerCreateRequest;
import com.back.sbb.answer.dto.AnswerModifyRequest;
import com.back.sbb.answer.dto.AnswerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.back.sbb.question.Question;
import com.back.sbb.question.QuestionService;
import com.back.sbb.user.SiteUser;
import com.back.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.boot.origin.Origin.from;

@RequestMapping("/api/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public AnswerResponse create(
            @RequestBody @Valid AnswerCreateRequest req,
            Principal principal
    ) {
        Question question = questionService.getQuestion(req.questionId());

        SiteUser user = userService.getUser(principal.getName());

        Answer answer = answerService.create(
                question,
                req.content(),
                user
        );

        return AnswerResponse.from(answer);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}")
    public AnswerResponse modify(
            @PathVariable("id") Integer id,
            @RequestBody @Valid AnswerModifyRequest req,
            Principal principal
    ) {
        Answer answer = answerService.getAnswer(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        Answer modified = answerService.modify(answer, req.content());
        return AnswerResponse.from(modified);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Principal principal,
            @PathVariable("id") Integer id
    ) {
        Answer answer = answerService.getAnswer(id);

        if(!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        answerService.delete(answer);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public void vote(
            Principal principal,
            @PathVariable("id") Integer id
    ) {
        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUser(principal.getName());

        answerService.vote(answer, user);
    }
}