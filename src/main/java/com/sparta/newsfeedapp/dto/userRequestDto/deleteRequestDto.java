package com.sparta.newsfeedapp.dto.userRequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class deleteRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "대소문자 포함 영문 + 숫자만 허용됩니다.")
    @Size(min = 10, max = 20, message = "사용자 ID는 최소 10글자 이상, 최대 20글자 이하여야 합니다.")
    @NotBlank(message = "userId는 필수입니다.")
    private String userId;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{10,}$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
    @Size(min = 10, message = "비밀번호는 최소 10글자 이상이어야 합니다.")
    @NotBlank(message = "paaword는 필수입니다.")
    private String password;
}
