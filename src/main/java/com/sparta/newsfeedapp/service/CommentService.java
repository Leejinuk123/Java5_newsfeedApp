package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.CommentRequestDto;
import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.entity.Newsfeed;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.repository.CommentRepository;
import com.sparta.newsfeedapp.repository.NewsfeedRepository;
import com.sparta.newsfeedapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.LocalDateTime;
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
        comment.setUserId(user);

        Newsfeed newsfeed = newsfeedRepository.findById(newsfeedId).orElseThrow(NullPointerException::new);
        System.out.println(newsfeed.getId());
        comment.setNewsfeedId(newsfeed);

        commentRepository.save(comment);
        return comment;
    }

    public LocalDateTime timeNow(){
        return LocalDateTime.now();
    }
}
