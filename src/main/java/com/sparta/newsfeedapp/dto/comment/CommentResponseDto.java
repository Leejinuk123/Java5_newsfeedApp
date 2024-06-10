package com.sparta.newsfeedapp.dto.comment;

import com.sparta.newsfeedapp.entity.Comment;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Integer countLiked;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.countLiked = comment.getCountLiked();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}
