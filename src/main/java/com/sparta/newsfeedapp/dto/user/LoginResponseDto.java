package com.sparta.newsfeedapp.dto.user;

import lombok.Getter;

@Getter
public class LoginResponseDto {
//    private String message;
    private String token;
    private String refreshToken;

    public LoginResponseDto(String token, String refreshToken) {
//        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
