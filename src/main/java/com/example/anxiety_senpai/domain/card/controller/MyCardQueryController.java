//package com.example.anxiety_senpai.domain.card.controller;
//
//import com.example.anxiety_senpai.domain.card.dto.CardListItemResponse;
//import com.example.anxiety_senpai.domain.card.dto.PageResponse;
//import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
//import com.example.anxiety_senpai.domain.card.service.CardQueryService;
//import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
//import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
//import com.example.anxiety_senpai.global.auth.CustomUserDetails;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/me/cards")
//public class MyCardQueryController {
//
//    private final CardQueryService cardQueryService;
//
//    @GetMapping
//    public ApiResponse<PageResponse<CardListItemResponse>> getMyCards(
//            @AuthenticationPrincipal CustomUserDetails user,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "latest") String sort,
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) SolveStatus solveStatus
//    ) {
//        Long userId = (user == null) ? null : user.getUserId();
//        // 인증이 필수이므로 null이면 스프링 시큐리티에서 401 처리됨
//
//        return ApiResponse.onSuccess(
//                GeneralSuccessCode.FOUND,
//                PageResponse.of(cardQueryService.getMyCards(userId, page, size, sort, keyword, solveStatus))
//        );
//    }
//}
//
