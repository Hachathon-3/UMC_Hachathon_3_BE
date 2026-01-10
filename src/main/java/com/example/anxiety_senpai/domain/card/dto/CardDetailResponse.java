package com.example.anxiety_senpai.domain.card.dto;

import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;

import java.time.LocalDateTime;
import java.util.List;

public record CardDetailResponse(
        Long cardId,
        String title,
        String content,
        CardStatus status,
        SolveStatus solveStatus,
        boolean allowComment,
        AuthorResponse author,
        List<TagResponse> tags,
        long reactionCount,
        boolean liked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
}
