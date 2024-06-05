package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.Newsfeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {
    List<Newsfeed> findAllByOrderByCreatedAtDesc();
    List<Newsfeed> findAllById(Long id);
}
