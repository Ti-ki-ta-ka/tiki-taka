package com.teamsparta.tikitaka.domain.match.service.v2.matchapplication2

import com.teamsparta.tikitaka.domain.match.dto.matchapplication.MatchApplicationResponse
import com.teamsparta.tikitaka.domain.match.dto.matchapplication.MyApplicationsResponse
import com.teamsparta.tikitaka.domain.match.dto.matchapplication.ReplyApplicationRequest
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface MatchApplicationService2 {
    fun applyMatch(userId: Long, matchId: Long): MatchApplicationResponse

    fun replyMatchApplication(
        userId: Long,
        matchId: Long,
        applicationId: Long,
        request: ReplyApplicationRequest
    ): MatchApplicationResponse


    fun getMyApplications(userId: Long): List<MyApplicationsResponse>

    fun cancelMatchApplication(
        principal: UserPrincipal, matchId: Long, applicationId: Long
    )
}