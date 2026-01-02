package com.back.sbb.answer.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerCreateRequest(
        Integer questionId,
        @NotBlank String content
) {}