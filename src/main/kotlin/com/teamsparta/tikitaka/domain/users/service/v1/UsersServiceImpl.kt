package com.teamsparta.tikitaka.domain.users.service.v1

import com.teamsparta.tikitaka.domain.common.exception.InvalidCredentialException
import com.teamsparta.tikitaka.domain.common.util.RedisUtils
import com.teamsparta.tikitaka.domain.users.dto.LoginRequest
import com.teamsparta.tikitaka.domain.users.dto.LoginResponse
import com.teamsparta.tikitaka.domain.users.dto.SignUpRequest
import com.teamsparta.tikitaka.domain.users.dto.UserDto
import com.teamsparta.tikitaka.domain.users.model.Users
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val redisUtils: RedisUtils
) : UsersService
{
    @Transactional
    override fun signUp(request: SignUpRequest): UserDto {
        if (usersRepository.findByEmail(request.email) != null) {
            throw InvalidCredentialException("중복된 이메일입니다.")
        }
        if (request.password != request.confirmPassword) {
            throw InvalidCredentialException("비밀번호 확인이 비밀번호와 일치하지 않습니다.")
        }
        with(Users) {
            validateEmail(request.email)
            validatePassword(request.password)
            validateName(request.name)
        }
        val user = usersRepository.save(
            Users(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name
            )
        )
        return UserDto.fromEntity(user)
    }

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

    override fun logOut(token: String) {
        redisUtils.setDataExpire(token, "blacklisted")
    }

    fun isTokenBlacklisted(token: String): Boolean {
        return redisUtils.getData(token) != null
    }
}