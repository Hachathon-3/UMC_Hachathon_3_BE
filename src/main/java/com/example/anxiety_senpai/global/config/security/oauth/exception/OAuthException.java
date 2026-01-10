package com.example.anxiety_senpai.global.config.security.oauth.exception;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class OAuthException extends GeneralException {
    public OAuthException(BaseCode code) {
        super(code);
    }
}
