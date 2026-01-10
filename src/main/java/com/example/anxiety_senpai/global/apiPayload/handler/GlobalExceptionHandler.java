package com.example.anxiety_senpai.global.apiPayload.handler;

import com.example.anxiety_senpai.global.apiPayload.code.BaseCode;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralErrorCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<String>> handleGeneral(GeneralException e) {
        BaseCode code = e.getCode();
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, code.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiResponse<String>> handleValidation(Exception e) {
        String message = GeneralErrorCode.BAD_REQUEST.getMessage();
        if (e instanceof MethodArgumentNotValidException manve && manve.getBindingResult().hasErrors()) {
            message = manve.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof BindException be && be.getBindingResult().hasErrors()) {
            message = be.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleFallback(Exception e) {
        BaseCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, code.getMessage()));
    }
}

