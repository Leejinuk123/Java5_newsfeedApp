package com.sparta.newsfeedapp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeedapp.dto.LoginRequestDto;
import com.sparta.newsfeedapp.dto.LoginResponseDto;
import com.sparta.newsfeedapp.dto.SignupRequestDto;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.entity.UserStatusEnum;
import com.sparta.newsfeedapp.jwt.JwtUtil;
import com.sparta.newsfeedapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


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

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 등록
        User user = new User(userId, password, email, name, UserStatusEnum.ACTIVE);
        userRepository.save(user);
        log.info("회원가입 완료");
    }

    public User loadUserByUserId(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다: " + userId));
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userId;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userId = jwtUtil.getUserInfoFromToken(refreshToken).getId();

//        if(userId != null){
//            User user = this.userRepository.findByUserId(userId).get();
//            if(jwtUtil.isTokenValid(refreshToken, userId)){
//                 //accessToken 새로 발급
//                String accessToken = jwtUtil.createToken(user.getUserId());
//                 //refreshToken 새로 발급
//
//                LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, refreshToken);
//                new ObjectMapper().writeValue(response.getOutputStream(), loginResponseDto);
//            }
//        }
    }
}
