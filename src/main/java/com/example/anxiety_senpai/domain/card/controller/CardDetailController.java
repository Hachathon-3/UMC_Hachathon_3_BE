package com.example.anxiety_senpai.domain.card.controller;

import com.example.anxiety_senpai.domain.card.dto.CardDetailResponse;
import com.example.anxiety_senpai.domain.card.service.CardDetailService;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardDetailController {

    private final CardDetailService cardDetailService;

    @GetMapping("/{cardId}")
    public ApiResponse<CardDetailResponse> getCardDetail(
            @PathVariable Long cardId,
            @AuthenticationPrincipal User user
    ) {
        Long userId = (user == null) ? null : user.getId();

        return ApiResponse.onSuccess(
                GeneralSuccessCode.FOUND,
                cardDetailService.getCardDetail(cardId, userId)
        );
    }
}

