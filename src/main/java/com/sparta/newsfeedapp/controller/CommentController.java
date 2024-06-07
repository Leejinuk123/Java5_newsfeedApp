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
@Transactional
public class CommentController {
    CommentService commentService;

    //PathVariable 추가
    @PostMapping("/comments/{postId}")
    public CommentResponseDto createCommentDto(@RequestBody CommentCreateRequestDto requestDto, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        // Entity > ResponseDto 변환
        return commentService.createComment(requestDto, postId, userDetails.getUser());
    }

    //해당 게시글에 달린 댓글만 가져오기
//    @GetMapping("/comments/{postId}")
//    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
//        return commentService.getComments(postId).stream().map(CommentResponseDto::new).toList();
//    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@RequestBody CommentUpdateRequestDto requestDto,
                                                @PathVariable Long commentId){
        return commentService.updateComment(requestDto, commentId);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        return  commentService.deleteComment(commentId);
    }

}
