package com.sparta.newsfeedapp.entity;

import com.sparta.newsfeedapp.dto.commentRequestDto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "countLiked", nullable = false)
    private Long countLiked;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt;
    public Comment(CommentRequestDto commentRequestDto){
        this.id = commentRequestDto.getId();
        this.content = commentRequestDto.getContent();
        this.countLiked = commentRequestDto.getCountLiked();
    }

    public Comment() {

    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
