package com.teamsparta.tikitaka.domain.team.dto.request

data class UpdateTeamRequest(
    val name: String,
    val description: String,
    val region: String
)