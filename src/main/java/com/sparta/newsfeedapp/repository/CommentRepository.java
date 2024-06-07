package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.Comment;
import com.sparta.newsfeedapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT MAX(Comment .id) FROM Comment", nativeQuery = true)
    Long findMaxId();

    List<Comment> findByUserId(Long userId);

    List<Comment> findByPostId(Long postId);

}
