package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplyResponse
import com.teamsparta.tikitaka.domain.matchApplication.repository.MatchApplicationRepository
import org.springframework.stereotype.Service

@Service
class MatchApplicationServiceImpl(
    private val matchApplicationRepository: MatchApplicationRepository
): MatchApplicationService
{
    override fun applyMatch(match: Long): MatchApplyResponse {
        TODO()
    }
}