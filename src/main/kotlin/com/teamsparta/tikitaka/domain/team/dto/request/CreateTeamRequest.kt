package com.teamsparta.tikitaka.domain.team.dto.request

data class CreateTeamRequest(
    val name: String,
    val description: String,
    val region: String
)