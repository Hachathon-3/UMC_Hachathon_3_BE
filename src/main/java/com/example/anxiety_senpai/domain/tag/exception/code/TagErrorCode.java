package com.example.anxiety_senpai.domain.tag.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements BaseCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "tag400", "잘못된 요청 파라미터입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

