package com.teamsparta.tikitaka.domain.recruitment.dto

import com.teamsparta.tikitaka.domain.recruitment.model.RecruitType

data class PostRecruitmentRequest(
    val userId: Long,
    val teamId: Long,
    val recruitType: RecruitType,
    val quantity: Int,
    val content: String,
)