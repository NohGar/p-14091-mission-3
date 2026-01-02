package com.back.sbb.question.dto;

import jakarta.validation.constraints.NotBlank;

// 요청 DTO
public record QuestionCreateRequest(
        @NotBlank String subject,
        @NotBlank String content
) {}