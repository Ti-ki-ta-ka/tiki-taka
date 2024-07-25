package com.teamsparta.tikitaka.domain.recruitment.dto.recruitmentapplication

import com.teamsparta.tikitaka.domain.recruitment.model.recruitmentapplication.RecruitmentApplication
import java.time.LocalDateTime

data class RecruitmentApplicationResponse(
    val applicationId: Long,
    val userId: Long,
    val teamId: Long,
    val responseStatus: String,
    val createdAt: LocalDateTime

) {

    companion object {
        fun from(recruitmentApplication: RecruitmentApplication) = RecruitmentApplicationResponse(
            applicationId = recruitmentApplication.id!!,
            userId = recruitmentApplication.userId,
            teamId = recruitmentApplication.teamId,
            responseStatus = recruitmentApplication.responseStatus.toString(),
            createdAt = recruitmentApplication.createdAt
        )
    }
}