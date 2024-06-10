package com.sparta.newsfeedapp.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailRequestDto {
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "email은 필수입니다.")
    private String email;
}
