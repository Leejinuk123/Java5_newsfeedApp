package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.jwt.JwtUtil;
import com.sparta.newsfeedapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
@Component
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService jwtBlacklistService;

    // refresh token
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = request.getHeader("RefreshToken").substring(7);
//        log.info("Refresh token: " + refreshToken);

        // accessToken 유효성 확인
        if(jwtUtil.validateToken(refreshToken)){
            String userId = jwtUtil.extractUserId(refreshToken);
            User user = userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);

            // accessToken 새로 발급
            String newAccessToken = jwtUtil.createToken(user.getUserId());
            log.info("access token 새로 발급" + newAccessToken);
            //refreshToken 새로 발급
            String newRefreshToken = jwtUtil.createRefreshToken(user.getUserId());
            log.info("refresh token 새로 발급" + newRefreshToken);

            user.setRefreshToken(newRefreshToken);
            response.setHeader("Authorization", newAccessToken);
            response.setHeader("RefreshToken", newRefreshToken);
        }
    }
}
