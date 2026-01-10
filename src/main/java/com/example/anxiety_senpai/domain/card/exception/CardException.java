package com.example.anxiety_senpai.domain.card.exception;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class CardException extends GeneralException {
    public CardException(BaseCode code) {
        super(code);
    }
}

