package com.example.anxiety_senpai.global.config.security.oauth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String frontendUrl = "http://54.242.218.23:8080";

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        log.error("OAuth2 로그인 실패: {}", exception.getMessage(), exception);

        // 프론트엔드 에러 페이지로 리다이렉트
        String redirectUrl = frontendUrl + "/auth/error?message=" +
                java.net.URLEncoder.encode("로그인에 실패했습니다.", "UTF-8");
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
