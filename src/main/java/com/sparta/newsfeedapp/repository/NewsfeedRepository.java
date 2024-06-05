package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.Newsfeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsfeedRepository  extends JpaRepository<Newsfeed, Long> {
}
