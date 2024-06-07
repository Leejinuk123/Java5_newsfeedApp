package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.userRequestDto.SignupRequestDto;
import com.sparta.newsfeedapp.dto.userRequestDto.deleteRequestDto;
import com.sparta.newsfeedapp.dto.userRequestDto.updateRequestDto;
import com.sparta.newsfeedapp.dto.userResponseDto.ProfileResponseDto;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.entity.UserStatusEnum;
import com.sparta.newsfeedapp.jwt.JwtUtil;
import com.sparta.newsfeedapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService jwtBlacklistService;

    public void signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String name = requestDto.getName();
        String bio = requestDto.getBio();

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
        User user = new User(userId, password, email, name, bio, UserStatusEnum.ACTIVE);
        userRepository.save(user);
        log.info("회원가입 완료");
    }

    @Transactional
    public void deleteUser(deleteRequestDto requestDto, User user) {
        String userPassword = requestDto.getPassword();
        if (!passwordEncoder.matches(userPassword, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.deactivateUser();
    }

    public ProfileResponseDto getProfile(User user) {
        String userId = user.getUserId();
        String name = user.getName();
        String email = user.getEmail();
        String bio = user.getBio();
        if (user.getBio() == null) bio = "자기소개가 없습니다.";

        return new ProfileResponseDto(userId,name,bio,email);
    }

    @Transactional
    public void updateProfile(updateRequestDto requestDto, User user) {
        User checkUser = loadUserByUserId(user.getUserId());
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String newPassword = null;
        String newName = null;
        String newEmail = null;
        String newBio = null;

        if (requestDto.getNewPassword() != null){ //비밀번호가 널이 아니면 아래를 실행 ( 비밀번호가 있으면 아래를 실행 )
            if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())){
                throw new IllegalArgumentException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
            }
            newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        }
        newName = requestDto.getName();
        newEmail = requestDto.getEmail();
        newBio = requestDto.getBio();

        checkUser.update(newName, newEmail, newPassword, newBio);
    }

    public User loadUserByUserId(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다: " + userId));
    }

    // logout
    public void logout(HttpServletRequest request) throws IOException {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken").substring(7);

        User user = loadUserByUserId(jwtUtil.extractUserId(accessToken));
        user.setRefreshToken("logged out");


        LocalDateTime accessExpiration = jwtUtil.extractExpiration(accessToken).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        jwtBlacklistService.blacklistToken(accessToken, accessExpiration);
        LocalDateTime refreshExpiration = jwtUtil.extractExpiration(refreshToken).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        jwtBlacklistService.blacklistToken(refreshToken, refreshExpiration);

        SecurityContextHolder.clearContext();
        log.info("logout success");
    }
}
