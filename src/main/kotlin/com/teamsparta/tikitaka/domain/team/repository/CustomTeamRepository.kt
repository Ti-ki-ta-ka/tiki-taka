package com.teamsparta.tikitaka.domain.team.repository

import com.teamsparta.tikitaka.domain.team.model.Team

interface CustomTeamRepository {
    fun searchTeamListByName(name: String): List<Team>
}