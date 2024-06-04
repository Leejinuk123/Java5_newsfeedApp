package com.sparta.newsfeedapp.controller;


import com.sparta.newsfeedapp.dto.PostDto.NewsfeedRequestDto;
import com.sparta.newsfeedapp.dto.PostDto.NewsfeedResponseDto;
import com.sparta.newsfeedapp.service.NewsfeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posting")
public class NewsfeedController {

    public final NewsfeedService newsfeedService;

    @Autowired
    public NewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @PostMapping
    public NewsfeedResponseDto post(@RequestBody NewsfeedRequestDto requestDto) {
        return newsfeedService.createPost(requestDto);
    }
}
