package com.example.anxiety_senpai.domain.tag.dto;

import java.util.List;

public record CardTagListResponse(Long cardId, List<TagSimpleResponse> tags) {
    public record TagSimpleResponse(Long tagId, String name) {}
}

