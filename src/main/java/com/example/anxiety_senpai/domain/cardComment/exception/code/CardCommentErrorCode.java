package com.example.anxiety_senpai.domain.cardComment.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CardCommentErrorCode implements BaseCode {
    FORBIDDEN(HttpStatus.FORBIDDEN, "comment403", "댓글 작성이 허용되지 않은 게시물입니다."),
    INVALID_CONTENT(HttpStatus.BAD_REQUEST, "comment400", "댓글 내용을 입력해주세요."),
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "card404", "게시물을 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth401", "인증이 필요합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

