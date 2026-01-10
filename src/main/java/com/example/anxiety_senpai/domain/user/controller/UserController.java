package com.example.anxiety_senpai.domain.user.controller;

import com.example.anxiety_senpai.domain.user.dto.HomeResponse;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.service.UserService;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralErrorCode;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralSuccessCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;
import com.example.anxiety_senpai.global.apiPayload.handler.ApiResponse;
import com.example.anxiety_senpai.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @GetMapping("/home")
    @Override
    public ResponseEntity<ApiResponse<HomeResponse>> getHome(
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }
        Long userId = user.getId();
        HomeResponse body = userService.getHome(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(GeneralSuccessCode.FOUND, body));
    }

    @Override
    public ResponseEntity<ApiResponse<HomeResponse>> getHome() {
        return null;
    }
}
