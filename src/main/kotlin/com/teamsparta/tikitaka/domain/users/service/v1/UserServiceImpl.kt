package com.teamsparta.tikitaka.domain.users.service.v1

import com.teamsparta.tikitaka.domain.users.dto.LoginRequest
import com.teamsparta.tikitaka.domain.users.dto.LoginResponse
import com.teamsparta.tikitaka.domain.users.dto.NameRequest
import com.teamsparta.tikitaka.domain.users.dto.NameResponse
import com.teamsparta.tikitaka.domain.users.dto.PasswordRequest
import com.teamsparta.tikitaka.domain.users.dto.PasswordResponse
import com.teamsparta.tikitaka.domain.users.model.toUpdateNameResponse
import com.teamsparta.tikitaka.domain.users.model.toUpdatePasswordResponse
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import com.teamsparta.tikitaka.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val usersRepository: UsersRepository,
    private val jwtPlugin: JwtPlugin,
    private val passwordEncoder: PasswordEncoder
) : UsersService {
    override fun logIn(request: LoginRequest): LoginResponse {
        val users = usersRepository.findByEmail(request.email) ?: throw RuntimeException("email이 없습니다")

        if (!passwordEncoder.matches(request.password, users.password)) {
            throw RuntimeException("비밀번호가 맞지 않습니다")
        }

        return LoginResponse(
            refreshToken = jwtPlugin.generateRefreshToken(
                subject = users.id.toString(),
                email = users.email
            ),
            accessToken = jwtPlugin.generateAccessToken(
                subject = users.id.toString(),
                email = users.email
            )

        )
    }

    @Transactional
    override fun updateName(
        request: NameRequest,
        userPrincipal: UserPrincipal
    ): NameResponse {
        val user = usersRepository.findByIdOrNull(userPrincipal.id) ?: throw RuntimeException("임시")
        if (user.id != userPrincipal.id) {
            throw RuntimeException("인증되지 않은 사용자")
        }
        if (usersRepository.existsByName(request.name)) {
            throw RuntimeException("이미 사용하고 있는 이름")
        }
        if (user.name == null) throw RuntimeException("값을 입력해주세요")

        user.name = request.name
        return user.toUpdateNameResponse()
    }

    @Transactional
    override fun updatePassword(
        request: PasswordRequest,
        userPrincipal: UserPrincipal
    ): PasswordResponse {
        val user = usersRepository.findByIdOrNull(userPrincipal.id) ?: throw RuntimeException("임시")
        if (user.id != userPrincipal.id) {
            throw RuntimeException("인증되지 않은 사용자")
        }
        if (usersRepository.existsByPassword(request.password)) {
            throw RuntimeException("이미 사용하고 있는 패스워드")
        }
        if (user.password == null) throw RuntimeException("값을 입력해주세요")

        user.password = passwordEncoder.encode(request.password)

        return user.toUpdatePasswordResponse()
    }
}