package com.example.anxiety_senpai.global.auth.controller;

import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.exception.code.AuthSuccessCode;
import com.example.anxiety_senpai.global.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @Override
    @GetMapping("/login/naver")
    public void naverLoginInfo(){}

    @Override
    @GetMapping("/login/kakao")
    public void kakaoLoginInfo(){}

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response){
        return authService.refresh(request,response);
    }

    // 로그아웃
    @Override
    @PostMapping("/logout")
    public ApiResponse<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        authService.logout(request, response);
        AuthSuccessCode code = AuthSuccessCode.LOGOUT_SUCCESS;
        return ApiResponse.onSuccess(code,null);
    }
}
