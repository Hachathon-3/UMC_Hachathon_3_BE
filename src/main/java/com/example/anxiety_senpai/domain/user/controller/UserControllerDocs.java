package com.example.anxiety_senpai.domain.user.controller;

import com.example.anxiety_senpai.domain.user.dto.HomeResponse;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserControllerDocs  {

    @GetMapping("/home")
    ResponseEntity<ApiResponse<HomeResponse>> getHome(
            User user
    );

    @Operation(
            summary = "홈 - 진행률/캣닢 조회",
            description = "로그인한 사용자의 progressPercent와 catnipCount를 반환합니다.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    ResponseEntity<ApiResponse<HomeResponse>> getHome();
}
