package com.example.anxiety_senpai.domain.cardComment.exception;

import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;
import com.example.anxiety_senpai.domain.cardComment.exception.code.CardCommentErrorCode;

public class CardCommentException extends GeneralException {
    public CardCommentException(CardCommentErrorCode code) {
        super(code);
    }
}

