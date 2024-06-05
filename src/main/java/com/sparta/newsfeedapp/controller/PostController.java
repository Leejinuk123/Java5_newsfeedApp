package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.postRequestDto.PostRequestDto;
import com.sparta.newsfeedapp.dto.postResponseDto.PostResponseDto;
import com.sparta.newsfeedapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posting")
public class PostController {

    public final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 뉴스피드 생성
    @PostMapping
    public PostResponseDto post(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 뉴스피드 전체 조회
    @GetMapping("/get")
    public List<PostResponseDto> getAll() {
        return postService.getPostAll();
    }

    // 뉴스피드 일부 조회
    @GetMapping("/{id}")
    public List<PostResponseDto> chooseNewsfeed(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/update/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
