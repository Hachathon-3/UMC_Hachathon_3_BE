package com.example.anxiety_senpai.global.apiPayload.handler;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ErrorResponse<T> {
    @JsonProperty("isSuccess")
    private final boolean successful;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private T result;

    public static <T> ErrorResponse<T> onFailure(BaseCode code, T result) {
        return new ErrorResponse<>(false, code.getCode(), code.getMessage(), result);
    }
}

