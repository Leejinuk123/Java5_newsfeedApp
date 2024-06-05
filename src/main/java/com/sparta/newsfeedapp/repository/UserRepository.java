package com.sparta.newsfeedapp.repository;

import com.sparta.newsfeedapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

}
