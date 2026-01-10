package com.example.anxiety_senpai.domain.cardComment.dto;

import com.example.anxiety_senpai.domain.cardComment.enums.CardCommentStatus;

import java.time.LocalDateTime;

public record CardCommentQueryResponse(
        Long commentId,
        Long cardId,
        String content,
        CardCommentStatus status,
        Author author,
        long reactionCount,
        boolean liked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public record Author(Long userId, String name) {}
}
