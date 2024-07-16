package com.teamsparta.tikitaka.domain.matchApplication.dto

import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication

data class MatchApplyResponse(
    val id: Long,
    val applyUserId: Long,
    val matchPost: Match,
    val applyTeamId: Long,
    val approveStatus: String
) {
    companion object {
        fun from(matchApplication: MatchApplication): MatchApplyResponse {
            return MatchApplyResponse(
                id = matchApplication.id!!,
                applyUserId = matchApplication.applyUserId,
                matchPost = matchApplication.matchPost,
                applyTeamId = matchApplication.applyTeamId,
                approveStatus = matchApplication.approveStatus.name,
            )
        }
    }
}