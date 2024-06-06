package com.sparta.newsfeedapp.dto;

import com.sparta.newsfeedapp.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private Long id;
    private Long user;
    private Long newsfeed;
    private String content;
    private Long countLiked;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CommentRequestDto(Comment comment){
        this.id = comment.getId();
        this.user = comment.getUser().getId();
        this.newsfeed = comment.getNewsfeed().getId();
        this.content = comment.getContent();
        this.countLiked = comment.getCountLiked();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
