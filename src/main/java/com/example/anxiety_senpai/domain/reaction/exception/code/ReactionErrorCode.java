package com.example.anxiety_senpai.domain.reaction.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReactionErrorCode implements BaseCode {
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "card404", "게시물을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "comment404", "댓글을 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth401", "인증이 필요합니다."),
    DUPLICATE_REACTION(HttpStatus.CONFLICT, "reaction409", "이미 공감을 남긴 게시물입니다."),
    DUPLICATE_COMMENT_REACTION(HttpStatus.CONFLICT, "commentReaction409", "이미 공감을 남긴 댓글입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
