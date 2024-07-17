package com.teamsparta.tikitaka.domain.users.service.v1

import com.teamsparta.tikitaka.domain.users.dto.LoginRequest
import com.teamsparta.tikitaka.domain.users.dto.LoginResponse
import com.teamsparta.tikitaka.domain.users.dto.NameRequest
import com.teamsparta.tikitaka.domain.users.dto.NameResponse
import com.teamsparta.tikitaka.domain.users.dto.PasswordRequest
import com.teamsparta.tikitaka.domain.users.dto.PasswordResponse
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface UsersService {

    fun logIn(request: LoginRequest): LoginResponse

    fun updateName(request: NameRequest, userPrincipal: UserPrincipal): NameResponse

    fun updatePassword(request: PasswordRequest, userPrincipal: UserPrincipal): PasswordResponse
}