package com.sparta.newsfeedapp.service;


import com.sparta.newsfeedapp.dto.PostDto.NewsfeedRequestDto;
import com.sparta.newsfeedapp.dto.PostDto.NewsfeedResponseDto;
import com.sparta.newsfeedapp.entity.Newsfeed;
import com.sparta.newsfeedapp.repository.NewsfeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsfeedService {

    public final NewsfeedRepository newsfeedRepository;

    @Autowired
    public NewsfeedService(NewsfeedRepository postRepository) {
        this.newsfeedRepository = postRepository;
    }

    public NewsfeedResponseDto createPost(NewsfeedRequestDto requestDto) {
        Newsfeed newsfeed = new Newsfeed(requestDto);

        newsfeedRepository.save(newsfeed);

        NewsfeedResponseDto responseDto = new NewsfeedResponseDto(newsfeed);

        return responseDto;
    }

}
