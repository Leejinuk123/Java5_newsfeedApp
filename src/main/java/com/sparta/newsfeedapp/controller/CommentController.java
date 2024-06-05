package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.CommentRequestDto;
import com.sparta.newsfeedapp.dto.CommentResponseDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.entity.Newsfeed;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.repository.CommentRepository;
import com.sparta.newsfeedapp.repository.NewsfeedRepository;
import com.sparta.newsfeedapp.repository.UserRepository;
import com.sparta.newsfeedapp.service.CommentService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
//@Transactional
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NewsfeedRepository newsfeedRepository;
    @Autowired
    CommentService commentService;
    private final Map<Long, Comment> commentList = new HashMap<>();

    @PostMapping("/create")
    public CommentResponseDto createCommentDto(@RequestBody CommentRequestDto requestDto,
                                               @RequestParam Long userId,
                                               @RequestParam Long newsfeedId){

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setCountLiked(requestDto.getCountLiked());

        // RequestDto > Entity

        //commentList.put(comment.getId(), comment);
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        comment.setUserId(user);

        Newsfeed newsfeed = newsfeedRepository.findById(newsfeedId).orElseThrow(NullPointerException::new);
        System.out.println(newsfeed.getId());
        comment.setNewsfeedId(newsfeed);

        commentRepository.save(comment);

        // Entity > ResponseDto 변환
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }


}
