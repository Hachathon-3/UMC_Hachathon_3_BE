package com.example.anxiety_senpai.domain.cardComment.dto;

import com.example.anxiety_senpai.domain.card.dto.PageResponse;
import java.util.List;

public record CardCommentListResponse(
        List<CardCommentQueryResponse> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static CardCommentListResponse of(PageResponse<CardCommentQueryResponse> page) {
        return new CardCommentListResponse(
                page.items(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages(),
                page.hasNext()
        );
    }
}

