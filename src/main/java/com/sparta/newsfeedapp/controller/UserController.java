package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.userRequestDto.SignupRequestDto;
import com.sparta.newsfeedapp.dto.userRequestDto.deleteRequestDto;
import com.sparta.newsfeedapp.dto.userRequestDto.updateRequestDto;
import com.sparta.newsfeedapp.dto.userResponseDto.ProfileResponseDto;
import com.sparta.newsfeedapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다.");
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id,@Valid @RequestBody deleteRequestDto requestDto){
        userService.deleteUser(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.");
    }

    @GetMapping("/user/{id}")
    public ProfileResponseDto getProfile(@PathVariable Long id){
        return userService.getProfile(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id, @Valid @RequestBody updateRequestDto requestDto){
        userService.updateProfile(id,requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("수정이 완료되었습니다.");
    }
}
