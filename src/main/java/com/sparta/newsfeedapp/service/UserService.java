package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.userRequestDto.SignupRequestDto;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.entity.UserStatusEnum;
import com.sparta.newsfeedapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String name = requestDto.getName();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUserId(userId);
        if (checkUsername.isPresent()) {
            User existUser = checkUsername.get();
            if (existUser.getUserStatus().equals(UserStatusEnum.DELETED)) {
                throw new IllegalArgumentException("탈퇴한 UserId입니다: " + userId);
            } else {
                throw new IllegalArgumentException("중복된 UserId가 존재합니다: " + userId);
            }
        }

        // 사용자 등록
        User user = new User(userId, password, email, name, UserStatusEnum.ACTIVE);
        userRepository.save(user);
        log.info("회원가입 완료");
    }
}
