package com.teamsparta.tikitaka.domain.match.dto.matchapplication

import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication

data class MatchApplicationResponse(
    val id: Long,
    val applyUserId: Long,
    val matchPost: Match,
    val applyTeamId: Long,
    val approveStatus: String
) {
    companion object {
        fun from(matchApplication: MatchApplication): MatchApplicationResponse {
            return MatchApplicationResponse(
                id = matchApplication.id!!,
                applyUserId = matchApplication.applyUserId,
                matchPost = matchApplication.matchPost,
                applyTeamId = matchApplication.applyTeamId,
                approveStatus = matchApplication.approveStatus.name,
            )
        }
    }
}