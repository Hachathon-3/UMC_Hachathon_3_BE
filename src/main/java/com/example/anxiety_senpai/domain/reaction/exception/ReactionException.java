package com.example.anxiety_senpai.domain.reaction.exception;

import com.example.anxiety_senpai.domain.reaction.exception.code.ReactionErrorCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class ReactionException extends GeneralException {
    public ReactionException(ReactionErrorCode code) {
        super(code);
    }
}

