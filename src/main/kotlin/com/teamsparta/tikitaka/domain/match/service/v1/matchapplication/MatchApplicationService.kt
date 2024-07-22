package com.teamsparta.tikitaka.domain.match.service.v1.matchapplication

import com.teamsparta.tikitaka.domain.match.dto.matchapplication.*
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface MatchApplicationService {
    fun applyMatch(userId: Long, request: CreateApplicationRequest, matchId: Long): MatchApplicationResponse

    fun replyMatchApplication(
        userId: Long,
        applicationId: Long,
        request: ReplyApplicationRequest
    ): MatchApplicationResponse


    fun getMyApplications(request: MyApplicationRequest): List<MyApplicationsResponse>

    fun deleteMatchApplication(
        principal: UserPrincipal, applicationId: Long
    )
}