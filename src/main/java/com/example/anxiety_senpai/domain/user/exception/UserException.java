package com.example.anxiety_senpai.domain.user.exception;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseCode code){
        super(code);
    }
}
