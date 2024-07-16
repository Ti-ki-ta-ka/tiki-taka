package com.teamsparta.tikitaka.domain.users.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @field:NotBlank
    @field:Size(min = 4, max = 40, message = "Email must be between 4 and 40 characters")
    @field:Pattern(
        regexp = "^[a-z0-9]+[a-z0-9._%+-]*@[a-z0-9.-]+\\.[a-z]{2,6}$",
        message = "이메일은 영어 소문자와 숫자 및 @로 구성되어야합니다."
    )
    val email: String,
    @field:NotBlank
    @field:Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
        message = "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자가 포함되어야 합니다."
    )
    val password: String,
    val confirmPassword: String,
    val name: String
)