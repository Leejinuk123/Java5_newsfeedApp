package com.sparta.newsfeedapp.service;


import com.sparta.newsfeedapp.dto.NewsfeedDto.NewsfeedRequestDto;
import com.sparta.newsfeedapp.dto.NewsfeedDto.NewsfeedResponseDto;
import com.sparta.newsfeedapp.entity.Newsfeed;
import com.sparta.newsfeedapp.repository.NewsfeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsfeedService {

    public final NewsfeedRepository newsfeedRepository;

    @Autowired
    public NewsfeedService(NewsfeedRepository postRepository) {
        this.newsfeedRepository = postRepository;
    }

    public NewsfeedResponseDto createPost(NewsfeedRequestDto requestDto) {
        // 인가 기능 추가하기

        Newsfeed newsfeed = new Newsfeed(requestDto);

        newsfeedRepository.save(newsfeed);

        NewsfeedResponseDto responseDto = new NewsfeedResponseDto(newsfeed);

        return responseDto;
    }

    public List<NewsfeedResponseDto> getAll() {
        return newsfeedRepository.findAllByOrderByCreatedAtDesc().stream().map(NewsfeedResponseDto :: new).toList();
    }

    public List<NewsfeedResponseDto> getNewsfeed(Long id) {
        return newsfeedRepository.findAllById(id).stream().map(NewsfeedResponseDto :: new).toList();
    }

    @Transactional
    public NewsfeedResponseDto updateNewsfeed(Long id, NewsfeedRequestDto requestDto) {
        Newsfeed newsfeed = findNewsfeed(id);

        // 인가 기능 추가하기

        newsfeed.update(requestDto);

        return new NewsfeedResponseDto(newsfeed);
    }

    public Long deleteNewsfeed(Long id) {
        Newsfeed newsfeed = findNewsfeed(id);

        // 인가 기능 추가하기


        newsfeedRepository.delete(newsfeed);

        return id;
    }

    private Newsfeed findNewsfeed(Long id) {
        return newsfeedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
    }



}
