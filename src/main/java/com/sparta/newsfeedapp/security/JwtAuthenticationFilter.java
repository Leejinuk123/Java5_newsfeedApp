package com.sparta.newsfeedapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeedapp.dto.user.LoginRequestDto;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.jwt.JwtUtil;
import com.sparta.newsfeedapp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.sparta.newsfeedapp.entity.UserStatusEnum.DELETED;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try{
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            User user = userRepository.findByUserId(loginRequestDto.getUserId()).orElseThrow(NullPointerException::new);
            if(user.getUserStatus() == DELETED){
                log.info("삭제된 사용자입니다");
                throw new IllegalArgumentException("삭제된 사용자입니다.");
            }

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUserId(),
                            loginRequestDto.getPassword(),
                            null

                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        // 토큰 생성
        String token = jwtUtil.createToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);
        // refresh token 유저에 저장
        User user = userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        // 헤더에 토큰 저장
        response.setHeader("Authorization", token);
        response.setHeader("RefreshToken", refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
