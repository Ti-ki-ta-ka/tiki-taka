package com.teamsparta.tikitaka.domain.users.service.v1

import com.teamsparta.tikitaka.domain.users.dto.SignUpRequest
import com.teamsparta.tikitaka.domain.users.dto.UserDto

interface UsersService {
    fun signUp(request: SignUpRequest): UserDto
}