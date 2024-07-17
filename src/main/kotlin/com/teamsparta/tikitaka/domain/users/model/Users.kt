package com.teamsparta.tikitaka.domain.users.model

import com.teamsparta.tikitaka.domain.users.dto.NameResponse
import com.teamsparta.tikitaka.domain.users.dto.PasswordResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

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
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Users.toUpdateNameResponse(): NameResponse = NameResponse(
    id = id!!,
    name = name
)

fun Users.toUpdatePasswordResponse(): PasswordResponse = PasswordResponse(
    id = id!!,
    password = password

)