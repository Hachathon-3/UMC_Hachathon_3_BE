package com.example.anxiety_senpai.global.auth.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseCode {

    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND,
            "AUTH404_1",
            "RefreshToken을 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,
            "AUTH400_1",
            "RefreshToken이 불일치합니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "AUTH404_2",
            "토큰에 저장된 해당 사용자를 찾을 수 없습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED,
            "AUTH401_2",
            "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED,
            "AUTH401_3",
            "만료된 JWT 토큰입니다."),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
