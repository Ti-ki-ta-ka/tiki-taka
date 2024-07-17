package com.teamsparta.tikitaka.domain.users.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
