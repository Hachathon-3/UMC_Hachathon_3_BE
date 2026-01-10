package com.example.anxiety_senpai.domain.card.dto;

import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.reaction.enums.ReactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record CardDetailResponse(
        Long cardId,
        String title,
        String content,
        CardStatus status,
        SolveStatus solveStatus,
        boolean allowComment,
        AuthorResponse author,
        List<TagResponse> tags,
        Map<ReactionType, Long> reactionSummary,
        List<ReactionType> myReactions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public record AuthorResponse(Long userId, String name) {}
    public record TagResponse(Long tagId, String name) {}
}

