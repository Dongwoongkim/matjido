package com.example.backend.global.config;

import com.example.backend.global.jwt.filter.JwtFilter;
import com.example.backend.global.jwt.handler.JwtAccessDeniedHandler;
import com.example.backend.global.jwt.handler.JwtAuthenticationFailHandler;
import com.example.backend.global.oauth.kakao.KakaoUserDetailsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoUserDetailsService kakaoUserDetailsService;
    private final JwtFilter jwtFilter;
    private final JwtAuthenticationFailHandler jwtAuthenticationFailHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 정적 리소스 무시 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 비밀번호 암호화에 사용할 Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 설정을 위한 SecurityFilterChain 등록
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(AbstractHttpConfigurer::disable)

            .cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))

            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/user/kakao-login/callback").permitAll()
                .requestMatchers("/api/exception/**").permitAll()
                .anyRequest().authenticated()
            )

            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .oauth2Login(oAuth2Login -> {
                oAuth2Login.userInfoEndpoint(userInfoEndpointConfig ->
                    userInfoEndpointConfig.userService(kakaoUserDetailsService));
            })

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationFailHandler);
                exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler);
            });

        return http.build();
    }

    /**
     * CORS CONFIG - 모든 출처(도메인)에서 오는 모든 HTTP 요청(GET, POST, PUT, DELETE, PATCH, OPTIONS)에 대해, CORS 요청 허용
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}