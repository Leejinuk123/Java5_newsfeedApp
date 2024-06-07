package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.commentRequestDto.CommentRequestDto;
import com.sparta.newsfeedapp.dto.commentResponseDto.CommentResponseDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    CommentService commentService;

    @PostMapping("/create")
    public CommentResponseDto createCommentDto(@RequestBody CommentRequestDto requestDto,
                                               @RequestParam Long userId,
                                               @RequestParam Long postId){

        Comment comment = commentService.createNewCommentColum(requestDto, userId, postId);
        // Entity > ResponseDto 변환
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    //만들긴 했는데 쓸 일이 없을 듯 함. 모든 댓글을 가져와도 쓸 데 없음.
    @GetMapping("/readAllComments")
    public List<CommentResponseDto> getAllComments(){
        return commentService.getAllComments().stream().map(CommentResponseDto::new).toList();
    }

    @GetMapping("/readOne")
    public List<CommentResponseDto> getCommentsByPostId(@RequestParam Long newsfeedId) {
        return commentService.getCommentsBynewsfeedId(newsfeedId).stream().map(CommentResponseDto::new).toList();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody CommentRequestDto requestDto,
                                                 @RequestParam Long commentId){
        return commentService.updateComment(requestDto, commentId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId){
        return  commentService.deleteComment(commentId);
    }

}
