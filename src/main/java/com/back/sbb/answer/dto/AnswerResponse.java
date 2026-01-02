package com.back.sbb.answer.dto;

import com.back.sbb.answer.Answer;

import java.time.LocalDateTime;

public record AnswerResponse(
        Integer id,
        String content,
        String author,
        LocalDateTime createDate
) {
    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getContent(),
                answer.getAuthor().getUsername(),
                answer.getCreateDate()
        );
    }
}
