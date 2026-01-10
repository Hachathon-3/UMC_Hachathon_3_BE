package com.example.anxiety_senpai.domain.card.dto;

import com.example.anxiety_senpai.domain.card.enums.SolveStatus;

import java.time.LocalDateTime;

public record CardListItemResponse(
        Long cardId,
        String title,
        String preview,
        SolveStatus solveStatus,
        boolean allowComment,
        AuthorResponse author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record AuthorResponse(Long userId, String name) {}
}