package com.teamsparta.tikitaka.domain.team.Service.v1

import com.teamsparta.tikitaka.domain.team.dto.request.TeamRequest
import com.teamsparta.tikitaka.domain.team.dto.response.PageResponse
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse

interface TeamService {


    fun createTeam(request: TeamRequest): TeamResponse

    fun updateTeam(request: TeamRequest, teamId: Long): TeamResponse

    fun deleteTeam(teamId: Long)

    fun getTeams(page: Int, size: Int, sortBy: String, direction: String): PageResponse<TeamResponse>

    fun getTeam(teamId: Long): TeamResponse

    fun searchTeamListByName(
        page: Int,
        size: Int,
        sortBy: String,
        direction: String,
        name: String
    ): PageResponse<TeamResponse>

}