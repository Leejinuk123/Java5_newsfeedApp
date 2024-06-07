package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.CommentRequestDto;
import com.sparta.newsfeedapp.dto.CommentResponseDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.repository.CommentRepository;
import com.sparta.newsfeedapp.repository.PostRepository;
import com.sparta.newsfeedapp.repository.UserRepository;
import com.sparta.newsfeedapp.security.UserDetailsImpl;
import com.sparta.newsfeedapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentService commentService;




    private final Map<Long, Comment> commentList = new HashMap<>();

    @PostMapping("/create")
    public CommentResponseDto createCommentDto(@RequestBody CommentRequestDto requestDto,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @PathVariable Long postId
                                               ){

        Comment comment = commentService.createNewCommentColum(requestDto, userDetails.getUser().getId(), postId);
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
    public List<CommentResponseDto> getCommentsBynewsfeedId(@RequestParam Long postId) {
        return commentService.getCommentsBynewsfeedId(postId).stream().map(CommentResponseDto::new).toList();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody CommentRequestDto requestDto,
                                                 @PathVariable Long commentId){
        return commentService.updateComment(requestDto, commentId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        return  commentService.deleteComment(commentId);
    }

}
