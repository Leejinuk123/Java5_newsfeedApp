package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.comment.CommentCreateRequestDto;
import com.sparta.newsfeedapp.dto.comment.CommentUpdateRequestDto;
import com.sparta.newsfeedapp.dto.comment.CommentResponseDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.entity.Post;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.repository.CommentRepository;
import com.sparta.newsfeedapp.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Long findMaxIdOfCommentTable(){
        return Optional.ofNullable(commentRepository.findMaxId()).orElse((long)0);

    }

    public CommentResponseDto createComment(CommentCreateRequestDto requestDto, Long postId, User user){
        // RequestDto > Entity
        Post checkPost = postRepository.findById(postId).orElseThrow(NullPointerException::new);
        Comment comment = new Comment(requestDto, user, checkPost);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> updateComment(CommentUpdateRequestDto requestDto, Long commentId, User user) {

        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
            if (!comment.getUser().getId().equals(user.getId())){
                throw new IllegalArgumentException("본인 댓글만 수정할 수 있습니다.");
            }
            comment.update(requestDto);
            return new ResponseEntity<>("성공적으로 수정했습니다. (" + comment.getContent() + ")", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment를 찾지 못해 수정하지 못했습니다.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteComment(Long commentId, User user){

        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
            if (!comment.getUser().getId().equals(user.getId())){
                throw new IllegalArgumentException("본인 댓글만 삭제할 수 있습니다.");
            }
            commentRepository.deleteById(commentId);
            return new ResponseEntity<>("성공적으로 삭제했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment를 찾지 못해 삭제하지 못했습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
