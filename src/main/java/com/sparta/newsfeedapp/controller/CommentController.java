package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.commentRequestDto.CommentRequestDto;
import com.sparta.newsfeedapp.dto.commentResponseDto.CommentResponseDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.security.UserDetailsImpl;
import com.sparta.newsfeedapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    CommentService commentService;

    //PathVariable 추가
    @PostMapping("/comments")
    public CommentResponseDto createCommentDto(@RequestBody CommentRequestDto requestDto,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment comment = commentService.createNewCommentColum(requestDto, userDetails.getUser());
        // Entity > ResponseDto 변환
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    //만들긴 했는데 쓸 일이 없을 듯 함. 모든 댓글을 가져와도 쓸 데 없음.
    @GetMapping("/comments")
    public List<CommentResponseDto> getAllComments(){
        return commentService.getAllComments().stream().map(CommentResponseDto::new).toList();
    }

    @GetMapping("/comments/{commentId}")
    public List<CommentResponseDto> getComments(@RequestBody Long postId) {
        return commentService.getComments(postId).stream().map(CommentResponseDto::new).toList();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@RequestBody CommentRequestDto requestDto,
                                                 @PathVariable Long commentId){
        return commentService.updateComment(requestDto, commentId);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        return  commentService.deleteComment(commentId);
    }

}
