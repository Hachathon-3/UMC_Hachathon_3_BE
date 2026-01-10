package com.example.anxiety_senpai.domain.mypage.dto;

import com.example.anxiety_senpai.domain.card.enums.SolveStatus;

import java.time.LocalDateTime;

public record CommentedCardResponse(
        Long cardId,
        String title,
        String preview,
        SolveStatus solveStatus,
        boolean allowComment,
        LocalDateTime lastCommentedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

