package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.Service.JwtBlacklistService;
import com.sparta.newsfeedapp.Service.UserService;
import com.sparta.newsfeedapp.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "AuthController")
@RequestMapping("/api/user")
public class AuthController {

    private JwtBlacklistService jwtBlacklistService;
    private JwtUtil jwtUtil;
    private UserService userService;

    // Refresh token
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

    // logout
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) throws IOException {

        String accessToken = request.getHeader("Authorization").substring(7);
        log.info("access token : " + accessToken);
        String refreshToken = request.getHeader("RefreshToken").substring(7);
        log.info("refreshtoken : " + refreshToken);

        LocalDateTime accessExpiration = jwtUtil.extractExpiration(accessToken).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        jwtBlacklistService.blacklistToken(accessToken, accessExpiration);
        LocalDateTime refreshExpiration = jwtUtil.extractExpiration(refreshToken).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        jwtBlacklistService.blacklistToken(refreshToken, refreshExpiration);

        SecurityContextHolder.clearContext();
        log.info("logout success");
    }
}
