package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT MAX(Comment .id) FROM Comment", nativeQuery = true)
    Long findMaxId();
}
