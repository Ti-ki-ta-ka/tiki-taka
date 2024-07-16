package com.teamsparta.tikitaka.domain.team.dto.response

import com.teamsparta.tikitaka.domain.team.model.Team

data class TeamResponse(
    val id: Long,
    val name: String,
    val description: String,
    val numMember: Int,
    val maxMember: Int,
    val tierScore: Int,
    val mannerScore: Int,
    val attendanceScore: Int,
    val recruitStatus: Boolean,
    val region: String
)

fun Team.toResponse(
    team: Team
): TeamResponse {
    return TeamResponse(
        team.id ?: throw IllegalStateException(" ID cannot be null"),
        team.name,
        team.description,
        team.numMember,
        team.maxMember,
        team.tierScore,
        team.mannerScore,
        team.attendanceScore,
        team.recruitStatus,
        team.region
    )
}