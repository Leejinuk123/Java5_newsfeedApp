package com.sparta.newsfeedapp.dto.userResponseDto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String userId;
    private String name;
    private String bio;
    private String email;

    public ProfileResponseDto(String userId, String name, String bio, String email) {
        this.userId = userId;
        this.name = name;
        this.bio = bio;
        this.email = email;
    }
}
