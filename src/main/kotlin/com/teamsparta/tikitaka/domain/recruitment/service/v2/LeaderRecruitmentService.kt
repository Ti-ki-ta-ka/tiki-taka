package com.teamsparta.tikitaka.domain.recruitment.service.v2

import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentRequest
import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentResponse
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface LeaderRecruitmentService {
    fun postRecruitment(principal: UserPrincipal, request: PostRecruitmentRequest): PostRecruitmentResponse
}