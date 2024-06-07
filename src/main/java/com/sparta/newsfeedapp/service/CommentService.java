package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.commentRequestDto.CommentCreateRequestDto;
import com.sparta.newsfeedapp.dto.commentRequestDto.CommentUpdateRequestDto;
import com.sparta.newsfeedapp.dto.commentResponseDto.CommentResponseDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.entity.Post;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.repository.CommentRepository;
import com.sparta.newsfeedapp.repository.PostRepository;
import com.sparta.newsfeedapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    CommentRepository commentRepository;
    UserRepository userRepository;
    PostRepository postRepository;

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

//    public List<Comment> getComments(long postId){
//        return commentRepository.findByPostId(postId);
//    }

    public ResponseEntity<String> updateComment(CommentUpdateRequestDto requestDto, Long commentId) {
        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
            comment.update(requestDto);
            return new ResponseEntity<>("성공적으로 수정했습니다. (" + comment.getContent() + ")", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment를 찾지 못해 수정하지 못했습니다.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteComment(Long commentId){
        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
            commentRepository.deleteById(commentId);
            return new ResponseEntity<>("성공적으로 삭제했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment를 찾지 못해 삭제하지 못했습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
