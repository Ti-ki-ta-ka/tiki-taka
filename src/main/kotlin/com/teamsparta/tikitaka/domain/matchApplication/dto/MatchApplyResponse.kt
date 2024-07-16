package com.teamsparta.tikitaka.domain.matchApplication.dto

import com.teamsparta.tikitaka.domain.match.model.Match

data class MatchApplyResponse(
    val id: Long,
    val matchPost: Match,
    val nickname: String,
    val applyTeamId: Long,
    val approveStatus: String
)
