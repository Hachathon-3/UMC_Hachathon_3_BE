package com.example.anxiety_senpai.domain.cardComment.dto;

import com.example.anxiety_senpai.domain.cardComment.enums.CardCommentStatus;
import com.example.anxiety_senpai.domain.reaction.enums.ReactionType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public record CardCommentQueryResponse(
        Long commentId,
        Long cardId,
        String content,
        CardCommentStatus status,
        Author author,
        Map<ReactionType, Long> reactionSummary,
        Set<ReactionType> myReactions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public record Author(Long userId, String name) {}
}

