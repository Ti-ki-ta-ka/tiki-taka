package com.teamsparta.tikitaka.domain.match.dto.matchapplication

import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication
import java.time.LocalDateTime

data class MatchApplicationsByIdResponse(
    val id: Long,
    val applyUserId: Long,
    val applyTeamId: Long,
    val approveStatus: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(matchApplication: MatchApplication): MatchApplicationsByIdResponse {
            return MatchApplicationsByIdResponse(
                id = matchApplication.id!!,
                applyUserId = matchApplication.applyUserId,
                applyTeamId = matchApplication.applyTeamId,
                approveStatus = matchApplication.approveStatus.name,
                createdAt = matchApplication.createdAt
            )
        }
    }
}