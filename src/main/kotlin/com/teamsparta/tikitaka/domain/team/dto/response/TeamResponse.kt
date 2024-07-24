package com.teamsparta.tikitaka.domain.team.dto.response

import com.teamsparta.tikitaka.domain.team.model.Team
import java.time.LocalDateTime

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
    val region: String,
    val createAt: LocalDateTime
) {
    companion object {
        fun from(
            team: Team
        ): TeamResponse {
            return TeamResponse(
                team.id ?: throw IllegalStateException("ID cannot be Null"),
                team.name,
                team.description,
                team.numMember,
                team.maxMember,
                team.tierScore,
                team.mannerScore,
                team.attendanceScore,
                team.recruitStatus,
                team.region.name,
                team.createdAt
            )
        }
    }
}

