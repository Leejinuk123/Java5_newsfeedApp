package com.sparta.newsfeedapp.entity;

import com.sparta.newsfeedapp.dto.commentRequestDto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    //컬럼명은 snake_case를 지향
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

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
