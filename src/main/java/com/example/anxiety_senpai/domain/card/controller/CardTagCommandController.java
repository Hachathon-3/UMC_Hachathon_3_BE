package com.example.anxiety_senpai.domain.card.controller;

import com.example.anxiety_senpai.domain.tag.dto.CardTagAddResponse;
import com.example.anxiety_senpai.domain.tag.dto.CardTagRemoveResponse;
import com.example.anxiety_senpai.domain.tag.dto.CardTagUpdateRequest;
import com.example.anxiety_senpai.domain.tag.dto.CardTagUpdateResponse;
import com.example.anxiety_senpai.domain.tag.exception.code.TagSuccessCode;
import com.example.anxiety_senpai.domain.tag.service.TagCommandService;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/tags")
@Validated
public class CardTagCommandController {

    private final TagCommandService tagCommandService;

    @PutMapping
    public ApiResponse<CardTagUpdateResponse> replaceTags(
            @PathVariable Long cardId,
            @AuthenticationPrincipal User user,
            @RequestBody @Validated CardTagUpdateRequest request
    ) {
        Long userId = user == null ? null : user.getId();
        CardTagUpdateResponse response = tagCommandService.replaceCardTags(cardId, userId, request);
        return ApiResponse.onSuccess(TagSuccessCode.CARD_TAG_UPDATED, response);
    }

    @PostMapping("/{tagId}")
    public ApiResponse<CardTagAddResponse> addTag(
            @PathVariable Long cardId,
            @PathVariable Long tagId,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "failOnDuplicate", defaultValue = "false") boolean failOnDuplicate
    ) {
        Long userId = user == null ? null : user.getId();
        CardTagAddResponse response = tagCommandService.addTagToCard(cardId, userId, tagId, failOnDuplicate);
        return ApiResponse.onSuccess(TagSuccessCode.CARD_TAG_ADDED, response);
    }

    @DeleteMapping("/{tagId}")
    public ApiResponse<CardTagRemoveResponse> removeTag(
            @PathVariable Long cardId,
            @PathVariable Long tagId,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "failIfMissing", defaultValue = "false") boolean failIfMissing
    ) {
        Long userId = user == null ? null : user.getId();
        CardTagRemoveResponse response = tagCommandService.removeTagFromCard(cardId, userId, tagId, failIfMissing);
        return ApiResponse.onSuccess(TagSuccessCode.CARD_TAG_REMOVED, response);
    }
}
