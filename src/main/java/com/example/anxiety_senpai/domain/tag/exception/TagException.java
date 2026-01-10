package com.example.anxiety_senpai.domain.tag.exception;

import com.example.anxiety_senpai.domain.tag.exception.code.TagErrorCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;

public class TagException extends GeneralException {
    public TagException(TagErrorCode code) {
        super(code);
    }
}

