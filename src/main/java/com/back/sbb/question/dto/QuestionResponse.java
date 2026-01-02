package com.back.sbb.question.dto;

import com.back.sbb.question.Question;

import java.time.LocalDateTime;

// 응답 DTO
public record QuestionResponse(
        Integer id,
        String subject,
        String content,
        String author,
        LocalDateTime createDate
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                question.getAuthor().getUsername(),
                question.getCreateDate()
        );
    }
}