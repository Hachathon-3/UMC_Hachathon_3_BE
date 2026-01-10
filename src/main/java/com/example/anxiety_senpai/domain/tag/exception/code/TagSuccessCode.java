package com.example.anxiety_senpai.domain.tag.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagSuccessCode implements BaseCode {
    CARD_TAG_UPDATED(HttpStatus.OK, "cardTag200", "게시물 태그가 변경되었습니다."),
    CARD_TAG_ADDED(HttpStatus.OK, "cardTag200", "태그가 추가되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

