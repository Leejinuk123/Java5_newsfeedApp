package com.sparta.newsfeedapp.service;

import com.sparta.newsfeedapp.dto.userRequestDto.SignupRequestDto;
import com.sparta.newsfeedapp.dto.userRequestDto.deleteRequestDto;
import com.sparta.newsfeedapp.dto.userRequestDto.updateRequestDto;
import com.sparta.newsfeedapp.dto.userResponseDto.ProfileResponseDto;
import com.sparta.newsfeedapp.entity.User;
import com.sparta.newsfeedapp.entity.UserStatusEnum;
import com.sparta.newsfeedapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    public void deleteUser(Long id, deleteRequestDto requestDto) {
        String userId =  requestDto.getUserId();
        String userPassword = requestDto.getPassword();

        User checkUser = loadUserById(id);
        if (!Objects.equals(checkUser.getUserId(), userId)){
            throw new IllegalArgumentException("유저아이디가 일치하지 않습니다.");
        }
        if (!passwordEncoder.matches(userPassword, checkUser.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        checkUser.deactivateUser();
    }

//    - **프로필 조회 기능**
//            - **사용자 ID, 이름, 한 줄 소개, 이메일**을 볼 수 있습니다.
//            - **ID(사용자 ID X), 비밀번호, 생성일자, 수정일자**와 같은 데이터는 노출하지 않습니다.
    public ProfileResponseDto getProfile(Long id) {
        User checkUser = loadUserById(id);
        String userId = checkUser.getUserId();
        String name = checkUser.getName();
        String email = checkUser.getEmail();
        String bio = checkUser.getBio();
        if (checkUser.getBio() == null) bio = "자기소개가 없습니다.";

        return new ProfileResponseDto(userId,name,bio,email);
    }

    /*
    * - 비밀번호 수정 조건
    - 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
    - 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.
    **  필수 예외처리
    - 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우
    - 비밀번호 형식이 올바르지 않은 경우
    - 현재 비밀번호와 동일한 비밀번호로 수정하는 경우
    * */
    @Transactional
    public void updateProfile(Long id, updateRequestDto requestDto) {
        User checkUser = loadUserById(id);
        if (!passwordEncoder.matches(checkUser.getPassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if (!passwordEncoder.matches(checkUser.getPassword(), requestDto.getNewPassword())){
            throw new IllegalArgumentException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
        }
        String newName = requestDto.getName();
        String newEmail = requestDto.getEmail();
        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        String newBio = requestDto.getBio();

        checkUser.update(newName, newEmail, newPassword, newBio);
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다: " + id));
    }
}
