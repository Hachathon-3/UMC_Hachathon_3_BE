package com.example.anxiety_senpai.global.auth.exception;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseCode code) {
        super(code);
    }
}
