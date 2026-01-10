package com.example.anxiety_senpai.domain.tag.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements BaseCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "tag400", "잘못된 요청 파라미터입니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "card404", "게시물을 찾을 수 없습니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "tag404", "존재하지 않는 태그입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth401", "인증이 필요합니다."),
    DUPLICATE_CARD_TAG(HttpStatus.CONFLICT, "cardTag409", "이미 연결된 태그입니다."),
    CARD_TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "cardTag404", "연결된 태그가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
