package com.sparta.newsfeedapp.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class EmailCheckRequestDto {
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "email은 필수입니다.")
    private String email;

    @NotEmpty(message = "인증 번호를 입력해 주세요")
    private String authNum;

}
