package com.teamsparta.tikitaka.domain.users.service.v1

import com.teamsparta.tikitaka.domain.users.dto.LoginRequest
import com.teamsparta.tikitaka.domain.users.dto.LoginResponse
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.jwt.JwtPlugin
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val usersRepository: UsersRepository,
    private val jwtPlugin: JwtPlugin
): UsersService
{
    override fun logIn(request: LoginRequest): LoginResponse {
        val user = usersRepository.findByEmail(request.email) ?: throw RuntimeException("임시")
        return LoginResponse(
            refreshToken = jwtPlugin.generateRefreshToken(
                subject = user.id.toString(),
                email = user.email
            ),
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email
            )

        )
    }
}