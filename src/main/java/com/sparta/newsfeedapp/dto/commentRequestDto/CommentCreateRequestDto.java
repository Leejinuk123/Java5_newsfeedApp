package com.sparta.newsfeedapp.dto.commentRequestDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateRequestDto {
    private String content;
}
