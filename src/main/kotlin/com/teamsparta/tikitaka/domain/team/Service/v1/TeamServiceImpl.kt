package com.teamsparta.tikitaka.domain.team.Service.v1

import com.teamsparta.tikitaka.domain.common.baseentity.exception.NotFoundException
import com.teamsparta.tikitaka.domain.team.dto.request.TeamRequest
import com.teamsparta.tikitaka.domain.team.dto.request.toEntity
import com.teamsparta.tikitaka.domain.team.dto.response.PageResponse
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse
import com.teamsparta.tikitaka.domain.team.repository.QueryDslTeamRepository
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamServiceImpl(
    val teamRepository: TeamRepository,
    val queryDslTeamRepository: QueryDslTeamRepository
) : TeamService {

    override fun searchTeamListByName(
        page: Int,
        size: Int,
        sortBy: String,
        direction: String,
        name: String
    ): PageResponse<TeamResponse> {
        val direction = getDirection(direction)
        val pageable: Pageable = PageRequest.of(page, size, direction, sortBy)
        val pageContent = teamRepository.findByName(pageable, name)



        return PageResponse(
            pageContent.content.map { TeamResponse.from(it) },
            page,
            size
        )
    }

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
        page: Int,
        size: Int,
        sortBy: String,
        direction: String
    ): PageResponse<TeamResponse> {
        val direction = getDirection(direction)
        val pageable: Pageable = PageRequest.of(page, size, direction, sortBy)
        val pageContent = teamRepository.findAllByPageable(pageable)
        return PageResponse(
            pageContent.content.map { TeamResponse.from(it) },
            page,
            size
        )
    }

    override fun getTeam(
        teamId: Long
    ): TeamResponse {
        val team = teamRepository.findByIdOrNull(teamId) ?: throw NotFoundException("team", teamId)
        return TeamResponse.from(team)
    }

    private fun getDirection(direction: String) = when (direction) {
        "asc" -> Sort.Direction.ASC
        else -> Sort.Direction.DESC
    }
}