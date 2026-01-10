package com.example.anxiety_senpai.domain.card.controller;

import com.example.anxiety_senpai.domain.card.dto.CardCreateRequest;
import com.example.anxiety_senpai.domain.card.dto.CardCreateResponse;
import com.example.anxiety_senpai.domain.card.exception.code.CardSuccessCode;
import com.example.anxiety_senpai.domain.card.service.CardService;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ApiResponse<CardCreateResponse> createCard(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CardCreateRequest request
    ) {
        Long cardId = cardService.create(user.getId(), request);

        return ApiResponse.onSuccess(
                CardSuccessCode.CREATED,
                new CardCreateResponse(cardId)
        );
    }
}