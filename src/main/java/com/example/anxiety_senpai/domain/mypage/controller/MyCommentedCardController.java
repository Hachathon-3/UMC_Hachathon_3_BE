package com.example.anxiety_senpai.domain.mypage.controller;

import com.example.anxiety_senpai.domain.card.dto.PageResponse;
import com.example.anxiety_senpai.domain.mypage.dto.CommentedCardResponse;
import com.example.anxiety_senpai.domain.mypage.service.MyCommentedCardService;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me/commented-cards")
public class MyCommentedCardController {

    private final MyCommentedCardService myCommentedCardService;

    @GetMapping
    public ApiResponse<PageResponse<CommentedCardResponse>> getMyCommentedCards(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "latest_comment") String sort
    ) {
        Long userId = user == null ? null : user.getUserId();
        return ApiResponse.onSuccess(
                GeneralSuccessCode.FOUND,
                PageResponse.of(myCommentedCardService.getCommentedCards(userId, page, size, sort))
        );
    }
}

