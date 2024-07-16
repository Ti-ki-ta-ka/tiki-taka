package com.teamsparta.tikitaka.domain.team.Service.v1

import com.teamsparta.tikitaka.domain.team.dto.request.TeamRequest
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse

interface TeamService {


    fun createTeam(request: TeamRequest): TeamResponse

    fun updateTeam(request: TeamRequest, teamId: Long): TeamResponse

    fun deleteTeam(teamId: Long)

    fun getTeams(): List<TeamResponse>

    fun getTeam(teamId: Long): TeamResponse

}