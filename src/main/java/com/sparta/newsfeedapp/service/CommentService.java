package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.CommentRequestDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.entity.Newsfeed;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.repository.CommentRepository;
import com.sparta.newsfeedapp.repository.NewsfeedRepository;
import com.sparta.newsfeedapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NewsfeedRepository newsfeedRepository;

    public Long findMaxIdOfCommentTable(){
        return Optional.ofNullable(commentRepository.findMaxId()).orElse((long)0);

    }

    public Comment createNewCommentColum(CommentRequestDto requestDto,
                                         Long userId,
                                         Long newsfeedId){
        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setCountLiked(requestDto.getCountLiked());

        // RequestDto > Entity

        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        comment.setUser(user);

        Newsfeed newsfeed = newsfeedRepository.findById(newsfeedId).orElseThrow(NullPointerException::new);
        System.out.println(newsfeed.getId());
        comment.setNewsfeed(newsfeed);

        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsBynewsfeedId(long newsfeedId){
        return commentRepository.findByNewsfeedId(newsfeedId);
    }

    public ResponseEntity<String> updateComment(CommentRequestDto requestDto ,Long commentId) {
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

    public LocalDateTime timeNow(){
        return LocalDateTime.now();
    }
}
