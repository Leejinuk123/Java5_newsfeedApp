package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.Newsfeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {
}
