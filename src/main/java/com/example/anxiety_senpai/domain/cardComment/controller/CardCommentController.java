package com.example.anxiety_senpai.domain.cardComment.controller;

import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentCreateRequest;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentCreateResponse;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentListResponse;
import com.example.anxiety_senpai.domain.cardComment.service.CardCommentService;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/comments")
@Validated
public class CardCommentController {

    private final CardCommentService cardCommentService;

    @PostMapping
    public ApiResponse<CardCommentCreateResponse> createComment(
            @PathVariable Long cardId,
            @AuthenticationPrincipal User user,
            @RequestBody @Validated CardCommentCreateRequest request
    ) {
        Long userId = (user == null) ? null : user.getId();
        CardCommentCreateResponse response = cardCommentService.createComment(cardId, userId, request);
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response);
    }

    @GetMapping
    public ApiResponse<CardCommentListResponse> getComments(
            @PathVariable Long cardId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sort", defaultValue = "latest") String sort,
            @AuthenticationPrincipal User user
    ) {
        Long userId = (user == null) ? null : user.getId();
        CardCommentListResponse response = cardCommentService.getComments(cardId, userId, page, size, sort);
        return ApiResponse.onSuccess(GeneralSuccessCode.FOUND, response);
    }
}
