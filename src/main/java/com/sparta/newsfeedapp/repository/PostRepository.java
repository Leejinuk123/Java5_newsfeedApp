package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.Post;
import com.sparta.newsfeedapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllById(Long id);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
