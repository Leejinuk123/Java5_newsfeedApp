package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.postRequestDto.PostRequestDto;
import com.sparta.newsfeedapp.dto.postResponseDto.PostResponseDto;
import com.sparta.newsfeedapp.entity.Post;
import com.sparta.newsfeedapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    public final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // 인가 기능 추가하기

        Post post = new Post(requestDto);

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto :: new).toList();
    }

    public List<PostResponseDto> getPost(Long id) {
        return postRepository.findAllById(id).stream().map(PostResponseDto :: new).toList();
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPostById(id);

        // 인가 기능 추가하기

        post.update(requestDto);

        return new PostResponseDto(post);
    }

    public Long deletePost(Long id) {
        Post post = findPostById(id);

        // 인가 기능 추가하기


        postRepository.delete(post);

        return id;
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
    }
}