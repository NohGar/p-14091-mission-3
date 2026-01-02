package com.back.sbb.answer.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerModifyRequest(
        @NotBlank String content
) {}
