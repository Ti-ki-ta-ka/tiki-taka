package com.teamsparta.tikitaka.domain.recruitment.dto

data class PostRecruitmentRequest(
    val userId: Long,
    val teamId: Long,
    val recruitType: String,
    val quantity: Int,
    val content: String,
)