package com.example.anxiety_senpai.domain.card.controller;

import com.example.anxiety_senpai.domain.tag.dto.CardTagListResponse;
import com.example.anxiety_senpai.domain.tag.service.TagQueryService;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/tags")
public class CardTagQueryController {

    private final TagQueryService tagQueryService;

    @GetMapping
    public ApiResponse<CardTagListResponse> getCardTags(@PathVariable Long cardId) {
        CardTagListResponse response = tagQueryService.getCardTags(cardId);
        return ApiResponse.onSuccess(GeneralSuccessCode.FOUND, response);
    }
}

