package com.sparta.newsfeedapp.controller;

import com.sparta.newsfeedapp.dto.email.EmailCheckRequestDto;
import com.sparta.newsfeedapp.dto.email.EmailRequestDto;
import com.sparta.newsfeedapp.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto) {
        System.out.println("이메일 인증 이메일 :" + emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }

    @PostMapping("/mailAuthCheck")
    public ResponseEntity<String> AuthCheck(@RequestBody @Valid EmailCheckRequestDto emailCheckDto){
        mailService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
        return ResponseEntity.status(HttpStatus.OK).body("인증이 완료되었습니다.");
    }
}
    