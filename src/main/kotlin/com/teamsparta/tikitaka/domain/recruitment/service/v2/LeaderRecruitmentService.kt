package com.teamsparta.tikitaka.domain.recruitment.service.v2

import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentRequest
import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentResponse
import com.teamsparta.tikitaka.domain.recruitment.dto.RecruitmentResponse
import com.teamsparta.tikitaka.domain.recruitment.dto.UpdateRecruitmentRequest
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface LeaderRecruitmentService {
    fun postRecruitment(principal: UserPrincipal, request: PostRecruitmentRequest): PostRecruitmentResponse
    fun updateRecruitmentPost(userId: Long, recruitmentId: Long, request: UpdateRecruitmentRequest): RecruitmentResponse
    fun closeRecruitmentPost(userId: Long, recruitmentId: Long): RecruitmentResponse

}