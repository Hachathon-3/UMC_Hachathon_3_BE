package com.example.anxiety_senpai.domain.card.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode implements BaseCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "CARD404_1",
            "사용자를 찾을 수 없습니다."),
    CARD_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,
            "CARD500_1",
            "카드 생성 중 오류가 발생했습니다."),
    INVALID_TAG_NAME(HttpStatus.BAD_REQUEST,
            "CARD400_1",
            "유효하지 않은 태그 이름입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "CARD404_2",
            "게시물을 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,
            "CARD403_1",
            "게시물에 대한 권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
