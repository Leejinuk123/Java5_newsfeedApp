package com.sparta.newsfeedapp.controller;


import com.sparta.newsfeedapp.dto.NewsfeedDto.NewsfeedRequestDto;
import com.sparta.newsfeedapp.dto.NewsfeedDto.NewsfeedResponseDto;
import com.sparta.newsfeedapp.service.NewsfeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posting")
public class NewsfeedController {

    public final NewsfeedService newsfeedService;

    @Autowired
    public NewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    // 뉴스피드 생성
    @PostMapping
    public NewsfeedResponseDto post(@RequestBody NewsfeedRequestDto requestDto) {
        return newsfeedService.createPost(requestDto);
    }

    // 뉴스피드 전체 조회
    @GetMapping("/get")
    public List<NewsfeedResponseDto> getAll() {
        return newsfeedService.getAll();
    }

    // 뉴스피드 일부 조회
    @GetMapping("/{id}")
    public List<NewsfeedResponseDto> chooseNewsfeed(@PathVariable Long id) {
        return newsfeedService.getNewsfeed(id);
    }

    @PutMapping("/update/{id}")
    public NewsfeedResponseDto update(@PathVariable Long id, @RequestBody NewsfeedRequestDto requestDto) {
        return newsfeedService.updateNewsfeed(id, requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return newsfeedService.deleteNewsfeed(id);
    }
}
