package com.example.anxiety_senpai.domain.tag.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CardTagUpdateRequest(
        @NotNull(message = "tagIds는 필수입니다.") List<Long> tagIds
) {}

