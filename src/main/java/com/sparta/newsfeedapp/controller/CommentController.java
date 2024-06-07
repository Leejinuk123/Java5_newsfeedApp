package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.commentRequestDto.CommentCreateRequestDto;
import com.sparta.newsfeedapp.dto.commentRequestDto.CommentUpdateRequestDto;
import com.sparta.newsfeedapp.dto.commentResponseDto.CommentResponseDto;
import com.sparta.newsfeedapp.security.UserDetailsImpl;
import com.sparta.newsfeedapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //PathVariable 추가
    @PostMapping("/comments/{postId}")
    public CommentResponseDto createComment(@RequestBody CommentCreateRequestDto requestDto, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        // Entity > ResponseDto 변환
        return commentService.createComment(requestDto, postId, userDetails.getUser());
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@RequestBody CommentUpdateRequestDto requestDto,
                                                @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(requestDto, commentId, userDetails.getUser());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return  commentService.deleteComment(commentId, userDetails.getUser());
    }

}