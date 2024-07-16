package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplyRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplyResponse
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface MatchApplicationService {
    fun applyMatch(request: CreateApplyRequest, matchId: Long): MatchApplyResponse
    fun deleteMatch(userId: Long, applicationId: Long)
}