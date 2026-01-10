package com.example.anxiety_senpai.global.auth.service;

import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.exception.UserException;
import com.example.anxiety_senpai.domain.user.exception.code.UserErrorCode;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import com.example.anxiety_senpai.global.auth.entity.RefreshToken;
import com.example.anxiety_senpai.global.auth.exception.AuthException;
import com.example.anxiety_senpai.global.auth.exception.code.AuthErrorCode;
import com.example.anxiety_senpai.global.auth.repository.RefreshTokenRepository;
import com.example.anxiety_senpai.global.config.security.jwt.JwtUtil;
import com.example.anxiety_senpai.global.util.CookieUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // OAuth 로그인 성공 후 AccessToken 생성
    @Transactional
    public void login(
            User user,
            HttpServletResponse response
    ) {
        Long userId = user.getId();
        String role = user.getRole().name();

        // access token 발급
        String accessToken =
                jwtUtil.createAccessToken(userId, role);

        // refresh token 발급
        String refreshToken =
                jwtUtil.createRefreshToken(userId);

        // refresh token DB 저장 (있으면 갱신)
        refreshTokenRepository.findById(userId)
                .ifPresentOrElse(
                        saved -> saved.updateToken(refreshToken),
                        () -> refreshTokenRepository.save(
                                RefreshToken.of(userId, refreshToken)
                        )
                );

        // 쿠키 설정 (여러 쿠키를 개별 헤더로 추가)
        String accessCookie = CookieUtil.accessToken(accessToken).toString();
        String refreshCookie = CookieUtil.refreshToken(refreshToken).toString();

        response.addHeader("Set-Cookie", accessCookie);
        response.addHeader("Set-Cookie", refreshCookie);
    }

    // AccessToken 재발급
    @Transactional
    public ResponseEntity<?> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // refresh 토큰 쿠키 확인
        String refreshToken = CookieUtil.get(request, "refreshToken");
        if (refreshToken == null) {
            throw new AuthException(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        // refresh JWT 검증
        Claims claims = jwtUtil.validateToken(refreshToken);
        Long userId = Long.valueOf(claims.getSubject());

        // DB에 저장된 refresh 토큰 확인
        RefreshToken saved = refreshTokenRepository.findById(userId)
                .orElseThrow(() ->
                        new AuthException(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN));

        if (!saved.getToken().equals(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        String newAccess =
                jwtUtil.createAccessToken(userId, user.getRole().name());

        // (권장) Refresh Token 회전
        String newRefresh =
                jwtUtil.createRefreshToken(userId);
        saved.updateToken(newRefresh);

        // 쿠키 재설정
        response.addHeader(
                "Set-Cookie",
                CookieUtil.accessToken(newAccess).toString()
        );
        response.addHeader(
                "Set-Cookie",
                CookieUtil.refreshToken(newRefresh).toString()
        );

        return ResponseEntity.ok().build();
    }

    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // RefreshToken이 있으면 DB에서 삭제
        try {
            String refreshToken = CookieUtil.get(request, "refreshToken");
            if (refreshToken != null) {
                Claims claims = jwtUtil.validateToken(refreshToken);
                Long userId = Long.valueOf(claims.getSubject());
                refreshTokenRepository.deleteById(userId);
            }
        } catch (AuthException e) {
            // 토큰이 만료되었거나 유효하지 않으면 무시 (쿠키만 삭제)
        }

        // 쿠키 삭제
        response.addHeader(
                "Set-Cookie",
                CookieUtil.delete("accessToken").toString()
        );
        response.addHeader(
                "Set-Cookie",
                CookieUtil.delete("refreshToken").toString()
        );
    }
}
