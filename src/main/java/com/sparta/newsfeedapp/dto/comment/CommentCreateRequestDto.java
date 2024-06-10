package com.sparta.newsfeedapp.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateRequestDto {
    private String content;
}
