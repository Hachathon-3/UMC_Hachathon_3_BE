package com.example.anxiety_senpai.domain.reaction.controller;

import com.example.anxiety_senpai.domain.reaction.dto.ReactionResponse;
import com.example.anxiety_senpai.domain.reaction.dto.ReactionSummaryResponse;
import com.example.anxiety_senpai.domain.reaction.exception.code.ReactionSuccessCode;
import com.example.anxiety_senpai.domain.reaction.service.ReactionService;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping
    public ApiResponse<ReactionResponse> react(
            @PathVariable Long cardId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = (user == null) ? null : user.getUserId();
        ReactionResponse response = reactionService.reactToCard(cardId, userId);
        return ApiResponse.onSuccess(ReactionSuccessCode.CREATED, response);
    }

    @GetMapping("/summary")
    public ApiResponse<ReactionSummaryResponse> getSummary(
            @PathVariable Long cardId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = (user == null) ? null : user.getUserId();
        ReactionSummaryResponse response = reactionService.getCardReactionSummary(cardId, userId);
        return ApiResponse.onSuccess(ReactionSuccessCode.SUMMARY_OK, response);
    }
}
