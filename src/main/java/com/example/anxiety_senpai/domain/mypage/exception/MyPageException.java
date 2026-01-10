package com.example.anxiety_senpai.domain.mypage.exception;

import com.example.anxiety_senpai.domain.mypage.exception.code.MyPageErrorCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class MyPageException extends GeneralException {
    public MyPageException(MyPageErrorCode code) {
        super(code);
    }
}

