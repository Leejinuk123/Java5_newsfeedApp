package com.sparta.newsfeedapp.config;

import com.sparta.newsfeedapp.exception.CustomAuthenticationEntryPoint;
import com.sparta.newsfeedapp.security.JwtRequestFilter;
import com.sparta.newsfeedapp.security.JwtAuthorizationFilter;
import com.sparta.newsfeedapp.security.JwtAuthenticationFilter;
import com.sparta.newsfeedapp.security.JwtService;
import com.sparta.newsfeedapp.repository.UserRepository;
import com.sparta.newsfeedapp.security.UserDetailsServiceImpl;
import com.sparta.newsfeedapp.service.JwtBlacklistService;
import com.sparta.newsfeedapp.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebSecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final JwtBlacklistService jwtBlacklistService;

    public WebSecurityConfig(JwtService jwtService, UserDetailsServiceImpl userDetailsService,
                             AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository, JwtBlacklistService jwtBlacklistService, UserService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtService, userDetailsService);
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtBlacklistService, jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/users/signup").permitAll()
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/auth/refresh-token").permitAll()
                        .requestMatchers("/api/posts").permitAll()
                        .requestMatchers("/api/posts/{postId}").permitAll()
                        .requestMatchers("/api/posts/{postId}/comments").permitAll()
                        // 서버 단에서 에러가 발생시 아래 url이 에러창을 띄워준다
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
        );

        http.exceptionHandling(auth -> {
            auth.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }
}