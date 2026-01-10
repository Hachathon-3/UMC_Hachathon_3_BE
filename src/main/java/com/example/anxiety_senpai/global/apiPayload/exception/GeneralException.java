package com.example.anxiety_senpai.global.apiPayload.exception;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private final BaseCode code;
}
