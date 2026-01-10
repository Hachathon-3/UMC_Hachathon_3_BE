package com.example.anxiety_senpai.domain.card.controller;

import com.example.anxiety_senpai.domain.card.dto.CardListItemResponse;
import com.example.anxiety_senpai.domain.card.dto.PageResponse;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.card.service.CardQueryService;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardQueryController {

    private final CardQueryService cardQueryService;

//    @GetMapping
//    public ApiResponse<PageResponse<CardListItemResponse>> getCards(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "latest") String sort,
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) Long tagId,
//            @RequestParam(required = false) SolveStatus solveStatus
//    ) {
//        return ApiResponse.onSuccess(
//                GeneralSuccessCode.FOUND,
//                PageResponse.of(cardQueryService.getCards(page, size, sort, keyword, tagId, solveStatus))
//        );
//    }
}