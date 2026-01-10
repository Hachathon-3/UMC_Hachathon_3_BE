package com.example.anxiety_senpai.domain.reaction.dto;

public record ReactionSummaryResponse(Long cardId, Long commentId, long count, boolean myReacted) {}

