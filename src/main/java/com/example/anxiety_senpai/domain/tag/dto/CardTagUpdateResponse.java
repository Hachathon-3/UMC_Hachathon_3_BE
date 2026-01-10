package com.example.anxiety_senpai.domain.tag.dto;

import java.util.List;

public record CardTagUpdateResponse(Long cardId, List<Long> tagIds) {}

