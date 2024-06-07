package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.Service.AuthService;
import com.sparta.newsfeedapp.Service.JwtBlacklistService;
import com.sparta.newsfeedapp.Service.UserService;
import com.sparta.newsfeedapp.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Slf4j(topic = "AuthController")
@RequestMapping("/api/user")
public class AuthController {

    private JwtBlacklistService jwtBlacklistService;
    private JwtUtil jwtUtil;
    private AuthService authService;

    // Refresh token
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    // logout
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) throws IOException {
        authService.logout(request);
    }
}
