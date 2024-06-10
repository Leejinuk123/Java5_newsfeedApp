package com.sparta.newsfeedapp.dto.post;

import com.sparta.newsfeedapp.entity.Post;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime ModifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.ModifiedAt = post.getModifiedAt();
    }
}