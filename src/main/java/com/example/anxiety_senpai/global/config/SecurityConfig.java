package com.example.anxiety_senpai.global.config;

import com.example.anxiety_senpai.global.config.security.oauth.CustomOAuth2UserService;
import com.example.anxiety_senpai.global.config.security.oauth.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    private static final String[] PERMIT_URLS = {
            "/",
            "/health",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger/login/**",
            "/oauth2/authorization/**",
            "/login/oauth2/code/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .successHandler(successHandler)
                );

        // JWT 필터 제거! 지금은 OAuth2만 테스트
        // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
