package com.example.anxiety_senpai.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CardCreateRequest(

        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        String content,

        Boolean allowComment,

        // 태그 자동 생성 정책
        List<String> tagNames
) {}