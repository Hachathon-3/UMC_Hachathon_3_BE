package com.example.anxiety_senpai.domain.tag.dto;

import com.example.anxiety_senpai.domain.card.dto.PageResponse;
import java.util.List;

public record TagListResponse(
        List<TagListItemResponse> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static TagListResponse of(PageResponse<TagListItemResponse> page) {
        return new TagListResponse(
                page.items(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages(),
                page.hasNext()
        );
    }
}

