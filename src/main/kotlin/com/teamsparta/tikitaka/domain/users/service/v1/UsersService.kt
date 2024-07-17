package com.teamsparta.tikitaka.domain.users.service.v1

import com.teamsparta.tikitaka.domain.users.dto.SignUpRequest
import com.teamsparta.tikitaka.domain.users.dto.UserDto
import com.teamsparta.tikitaka.domain.users.dto.LoginRequest
import com.teamsparta.tikitaka.domain.users.dto.LoginResponse

interface UsersService {
    fun signUp(request: SignUpRequest): UserDto
    fun logIn(request: LoginRequest): LoginResponse
    fun logOut(token: String)
    fun validateRefreshTokenAndCreateToken(refreshToken: String): LoginResponse
}