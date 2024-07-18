package com.teamsparta.tikitaka.domain.users.model

import com.teamsparta.tikitaka.domain.common.exception.InvalidCredentialException
import com.teamsparta.tikitaka.domain.users.dto.NameResponse
import com.teamsparta.tikitaka.domain.users.dto.PasswordResponse
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.regex.Pattern

@Entity
@Table(name = "app_user")
class Users(
    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "team_status")
    val teamStatus: Boolean = false,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime = LocalDateTime.now(),
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun Users.toUpdateNameResponse(): NameResponse = NameResponse(
        id = id!!,
        name = name
    )

    fun Users.toUpdatePasswordResponse(): PasswordResponse = PasswordResponse(
        id = id!!,
        password = password

    )

    companion object {
        fun validateName(newName: String) {
            if (newName.isBlank()) {
                throw InvalidCredentialException("이름을 입력해야 합니다.")
            }
        }

        fun validatePassword(newPassword: String) {
            if (newPassword.length !in 8..15) {
                throw InvalidCredentialException("비밀번호는 최소 8자 이상, 15자 이하여야만 합니다.")
            }
            if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,15}$", newPassword)) {
                throw InvalidCredentialException("비밀번호는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자가 포함되어야 합니다.")
            }
        }

        fun validateEmail(newEmail: String) {
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$", newEmail)) {
                throw InvalidCredentialException("이메일은 영어 소문자와 숫자 및 @로 구성되어야합니다.")
            }
        }
    }
}

