package com.teamsparta.tikitaka.domain.matchApplication.dto

import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication

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