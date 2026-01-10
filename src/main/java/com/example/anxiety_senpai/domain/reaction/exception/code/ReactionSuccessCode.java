package com.example.anxiety_senpai.domain.reaction.exception.code;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReactionSuccessCode implements BaseCode {
    CREATED(HttpStatus.CREATED, "reaction201", "공감이 등록되었습니다."),
    COMMENT_CREATED(HttpStatus.CREATED, "commentReaction201", "댓글 공감이 등록되었습니다."),
    SUMMARY_OK(HttpStatus.OK, "reaction200", "공감 집계 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
