package com.sparta.newsfeedapp.entity;

import com.sparta.newsfeedapp.dto.PostDto.NewsfeedRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "newsfeed")
public class Newsfeed extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    public Newsfeed(NewsfeedRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.content = requestDto.getContent();
    }
}
