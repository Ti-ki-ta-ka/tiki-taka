package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplicationResponse
import com.teamsparta.tikitaka.domain.matchApplication.dto.ReplyApplicationRequest

interface MatchApplicationService {
    fun applyMatch(userId: Long, request: CreateApplicationRequest, matchId: Long): MatchApplicationResponse

    // fun deleteMatchApplication(principal: UserPrincipal, applicationId: Long)
    fun replyMatchApplication(
        userId: Long,
        applicationId: Long,
        request: ReplyApplicationRequest
    ): MatchApplicationResponse
}