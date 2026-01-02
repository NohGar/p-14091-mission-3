package com.back.sbb.question;


import java.security.Principal;
import java.util.List;

import com.back.sbb.question.dto.QuestionCreateRequest;
import com.back.sbb.question.dto.QuestionResponse;
import com.back.sbb.user.SiteUser;
import com.back.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/questions")
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping
    public Page<QuestionResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String kw
    ) {
        return questionService.getList(page, kw)
                .map(QuestionResponse::from);
    }

    @GetMapping("/{id}")
    public QuestionResponse detail(
            @PathVariable("id") Integer id
    ) {
        return QuestionResponse.from(
                questionService.getQuestion(id)
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Valid @RequestBody QuestionCreateRequest req,
            Principal principal
    ) {
        SiteUser user = userService.getUser(principal.getName());
        questionService.create(req.subject(), req.content(), user);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public void modify(
            @PathVariable("id") Integer id,
            @Valid @RequestBody QuestionCreateRequest req,
            Principal principal
    ) {
        Question question = questionService.getQuestion(id);
        checkAuthor(question, principal);
        questionService.modify(question, req.subject(), req.content());
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Principal principal,
            @PathVariable("id") Integer id
    ) {
        Question question = questionService.getQuestion(id);
        checkAuthor(question, principal);
        questionService.delete(question);
    }

    private void checkAuthor(Question question, Principal principal) {
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


}
