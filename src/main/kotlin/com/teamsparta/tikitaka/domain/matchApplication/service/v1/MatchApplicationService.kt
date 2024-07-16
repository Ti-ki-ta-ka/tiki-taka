package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplyResponse

interface MatchApplicationService {
    fun applyMatch(match: Long): MatchApplyResponse
}