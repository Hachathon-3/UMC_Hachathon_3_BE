package com.example.anxiety_senpai.domain.cardComment.dto;

import jakarta.validation.constraints.NotBlank;

public record CardCommentCreateRequest(
        @NotBlank(message = "댓글 내용을 입력해주세요.")
        String content
) {}

