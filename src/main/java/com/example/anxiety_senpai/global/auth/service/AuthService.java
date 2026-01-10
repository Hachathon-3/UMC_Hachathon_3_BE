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
    public void login(User user, HttpServletResponse response) {
        try{
            Long userId = user.getId();
            String role = user.getRole().name();

            String accessToken = jwtUtil.createAccessToken(userId, role);
            String refreshToken = jwtUtil.createRefreshToken(userId);

            // refresh token DB 저장 (있으면 갱신)
            refreshTokenRepository.findById(userId)
                    .ifPresentOrElse(
                            saved -> saved.updateToken(refreshToken),
                            () -> refreshTokenRepository.save(
                                    RefreshToken.of(userId, refreshToken)
                            )
                    );

            // 쿠키 설정
            response.addHeader("Set-Cookie", CookieUtil.accessToken(accessToken).toString());
            response.addHeader("Set-Cookie", CookieUtil.refreshToken(refreshToken).toString());
        }
        catch (Exception e){
            throw new AuthException(AuthErrorCode.EXPIRED_ACCESS_TOKEN);
        }

    }

    @Transactional
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = CookieUtil.get(request, "refreshToken");
            if (refreshToken == null)
                return ResponseEntity.status(401).body("Refresh token not found");

            Claims claims = jwtUtil.validateToken(refreshToken);
            Long userId = Long.valueOf(claims.getSubject());

            RefreshToken saved = refreshTokenRepository.findById(userId).orElse(null);
            if (saved == null || !saved.getToken().equals(refreshToken))
                return ResponseEntity.status(401).body("Invalid refresh token");

            User user = userRepository.findById(userId).orElse(null);
            if (user == null) return ResponseEntity.status(404).body("User not found");

            String newAccess = jwtUtil.createAccessToken(userId, user.getRole().name());
            String newRefresh = jwtUtil.createRefreshToken(userId);
            saved.updateToken(newRefresh);

            response.addHeader("Set-Cookie", CookieUtil.accessToken(newAccess).toString());
            response.addHeader("Set-Cookie", CookieUtil.refreshToken(newRefresh).toString());

            return ResponseEntity.ok().build();

        } catch (AuthException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            // 그 외 예기치 않은 오류
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @Transactional
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = CookieUtil.get(request, "refreshToken");
            if (refreshToken != null) {
                Claims claims = jwtUtil.validateToken(refreshToken);
                Long userId = Long.valueOf(claims.getSubject());
                refreshTokenRepository.deleteById(userId);
            }
        } catch (Exception ignored) {
        }

        response.addHeader("Set-Cookie", CookieUtil.delete("accessToken").toString());
        response.addHeader("Set-Cookie", CookieUtil.delete("refreshToken").toString());

        return ResponseEntity.ok().build();
    }
}
