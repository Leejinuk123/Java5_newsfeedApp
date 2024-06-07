package com.sparta.newsfeedapp.entity;

import com.sparta.newsfeedapp.dto.postRequestDto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    public Post(PostRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.content = requestDto.getContent();
    }

    public void update(PostRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
