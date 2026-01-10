package com.example.anxiety_senpai.global.config.security.oauth.handler;

import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.auth.service.AuthService;
import com.example.anxiety_senpai.global.config.security.oauth.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;

    private String frontendUrl = "http://54.242.218.23:8080";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        try {
            //  OAuth 인증된 사용자 꺼내기
            CustomOAuth2User oAuth2User =
                    (CustomOAuth2User) authentication.getPrincipal();

            User user = oAuth2User.getUser();

            // 서비스 로그인 (JWT 발급 + 쿠키 세팅)
            authService.login(user, response);

            // OAuth2로 잠깐 인증했던 흔적을 지우고 JWT 인증 체계
            SecurityContextHolder.clearContext();

            // 프론트엔드로 리다이렉트
            String redirectUrl = frontendUrl + "/auth/callback";
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        } catch (Exception e) {
            log.error("OAuth2 로그인 성공 후 처리 중 오류 발생: {}", e.getMessage(), e);

            // 에러 발생 시 프론트엔드 에러 페이지로 리다이렉트
            String redirectUrl = frontendUrl + "/auth/error?message=" +
                    java.net.URLEncoder.encode("로그인 처리 중 오류가 발생했습니다.", "UTF-8");
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
    }
}
