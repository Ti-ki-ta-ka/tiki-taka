package com.teamsparta.tikitaka.domain.team.Service.v1

import com.teamsparta.tikitaka.domain.team.dto.request.CreateTeamRequest
import com.teamsparta.tikitaka.domain.team.dto.request.UpdateTeamRequest
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse

interface TeamService {


    fun createTeam(request: CreateTeamRequest): TeamResponse

    fun updateTeam(request: UpdateTeamRequest, teamId: Long): TeamResponse

    fun deleteTeam(teamId: Long)

    fun getTeams(): List<TeamResponse>

    fun getTeam(teamId: Long): TeamResponse

}