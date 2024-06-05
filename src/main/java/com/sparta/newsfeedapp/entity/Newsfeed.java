package com.sparta.newsfeedapp.entity;

import com.sparta.newsfeedapp.dto.NewsfeedDto.NewsfeedRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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

    public void update(NewsfeedRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
