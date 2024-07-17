package com.teamsparta.tikitaka.domain.team.dto.request

import com.teamsparta.tikitaka.domain.team.model.Team

data class TeamRequest(
    val name: String,
    val description: String,
    val region: String
)

fun TeamRequest.toEntity(
): Team {
    return Team(
        name = this.name,
        description = this.description,
        region = this.region
    )
}