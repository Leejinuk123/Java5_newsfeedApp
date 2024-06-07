package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.postRequestDto.PostRequestDto;
import com.sparta.newsfeedapp.dto.postResponseDto.PostResponseDto;
import com.sparta.newsfeedapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;

    // 뉴스피드 생성
    @PostMapping("/post")
    public PostResponseDto post(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 뉴스피드 전체 조회
    @GetMapping("/post")
    public List<PostResponseDto> getAll() {
        return postService.getPostAll();
    }

    // 뉴스피드 일부 조회
    @GetMapping("/post/{id}")
    public List<PostResponseDto> chooseNewsfeed(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
