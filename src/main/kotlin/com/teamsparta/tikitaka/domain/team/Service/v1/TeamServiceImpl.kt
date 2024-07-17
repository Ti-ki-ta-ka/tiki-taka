package com.teamsparta.tikitaka.domain.team.Service.v1

import com.teamsparta.tikitaka.domain.common.baseentity.exception.NotFoundException
import com.teamsparta.tikitaka.domain.team.dto.request.TeamRequest
import com.teamsparta.tikitaka.domain.team.dto.request.toEntity
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamServiceImpl(
    val teamRepository: TeamRepository
) : TeamService {


    @Transactional
    override fun createTeam(
        request: TeamRequest
    ): TeamResponse {
        return TeamResponse.from(teamRepository.save(request.toEntity()))
    }


    @Transactional
    override fun updateTeam(
        request: TeamRequest,
        teamId: Long
    ): TeamResponse {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)
        team.updateTeam(request.name, request.description, request.region)
        return TeamResponse.from(team)
    }

    @Transactional
    override fun deleteTeam(
        teamId: Long
    ) {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)
        team.softDelete()
    }

    override fun getTeams(
    ): List<TeamResponse> {
        val teams = teamRepository.findAll()
        return teams.map { team -> TeamResponse.from(team) }
    }

    override fun getTeam(
        teamId: Long
    ): TeamResponse {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)
        return TeamResponse.from(team)
    }

}