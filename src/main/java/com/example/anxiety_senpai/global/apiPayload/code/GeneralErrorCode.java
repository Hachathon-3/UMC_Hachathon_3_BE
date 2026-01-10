package com.example.anxiety_senpai.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode implements BaseCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth401", "인증이 필요합니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "card400", "요청 파라미터가 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server500", "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
