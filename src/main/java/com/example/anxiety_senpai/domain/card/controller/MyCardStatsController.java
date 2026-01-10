package com.example.anxiety_senpai.domain.card.controller;

import com.example.anxiety_senpai.domain.card.dto.MyCardStatsResponse;
import com.example.anxiety_senpai.domain.card.service.CardStatsService;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/summary")
public class MyCardStatsController {

    private final CardStatsService cardStatsService;

    @GetMapping
    public ApiResponse<MyCardStatsResponse> getMyCardStats(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user == null ? null : user.getUserId();
        MyCardStatsResponse response = cardStatsService.getMyStats(userId);
        return ApiResponse.onSuccess(GeneralSuccessCode.FOUND, response);
    }
}

