package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.commentResponseDto.CommentResponseDto;
import com.sparta.newsfeedapp.dto.postRequestDto.PostRequestDto;
import com.sparta.newsfeedapp.dto.postResponseDto.PostResponseDto;
import com.sparta.newsfeedapp.security.UserDetailsImpl;
import com.sparta.newsfeedapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;

    // 뉴스피드 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestDto, userDetails.getUser());
    }

    // 뉴스피드 전체 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getAllPost() {
        return postService.getAllPost();
    }

    // 뉴스피드 일부 조회
    @GetMapping("/posts/{id}")
    public List<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return postService.getComments(postId).stream().map(CommentResponseDto::new).toList();
    }

    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/posts/{id}")
    public Long deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }
}
