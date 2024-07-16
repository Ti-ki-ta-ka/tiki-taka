package com.teamsparta.tikitaka.domain.team.Service.v1

import com.teamsparta.tikitaka.domain.common.baseentity.exception.NotFoundException
import com.teamsparta.tikitaka.domain.team.dto.request.CreateTeamRequest
import com.teamsparta.tikitaka.domain.team.dto.request.UpdateTeamRequest
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse
import com.teamsparta.tikitaka.domain.team.dto.response.toResponse
import com.teamsparta.tikitaka.domain.team.model.Team
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TeamServiceImpl(
    val teamRepository: TeamRepository
) : TeamService {


    override fun createTeam(
        request: CreateTeamRequest
    ): TeamResponse {
        val team = Team(
            name = request.name,
            description = request.description,
            region = request.region
        )

        return teamRepository.save(team).toResponse(team)
    }

    override fun updateTeam(
        request: UpdateTeamRequest,
        teamId: Long
    ): TeamResponse {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)

        team.name = request.name
        team.description = request.description
        team.region = request.region

        return teamRepository.save(team).toResponse(team)
    }

    override fun deleteTeam(
        teamId: Long
    ) {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)
        teamRepository.delete(team)
    }

    override fun getTeams(

    ): List<TeamResponse> {
        val teams = teamRepository.findAll()
        return teams.map { team -> team.toResponse(team) }
    }

    override fun getTeam(
        teamId: Long
    ): TeamResponse {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)
        return team.toResponse(team)
    }

}