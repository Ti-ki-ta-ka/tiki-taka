package com.teamsparta.tikitaka.domain.recruitment.dto

import com.teamsparta.tikitaka.domain.recruitment.model.RecruitType
import com.teamsparta.tikitaka.domain.recruitment.model.Recruitment

data class PostRecruitmentResponse(
    val recruitmentId: Long,
    val recruitType: RecruitType,
    val closingStatus: Boolean,
) {

    companion object {
        fun from(recruitment: Recruitment) = PostRecruitmentResponse(
            recruitmentId = recruitment.id!!,
            recruitType = recruitment.recruitType,
            closingStatus = false,
        )
    }
}