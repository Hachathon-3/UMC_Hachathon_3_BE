package com.example.anxiety_senpai.domain.card.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardSuccessCode implements BaseCode {
    CREATED(HttpStatus.CREATED,
            "CARD201_1",
            "카드를 생성했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

