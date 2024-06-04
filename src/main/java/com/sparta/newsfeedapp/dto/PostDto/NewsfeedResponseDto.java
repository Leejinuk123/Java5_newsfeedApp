package com.sparta.newsfeedapp.dto.PostDto;

import com.sparta.newsfeedapp.entity.Newsfeed;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsfeedResponseDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public NewsfeedResponseDto(Newsfeed newsfeed) {
        this.id = newsfeed.getId();
        this.userId = newsfeed.getUserId();
        this.content = newsfeed.getContent();
        this.createdAt = newsfeed.getCreatedAt();
        this.editedAt = newsfeed.getEditedAt();
    }
}
