package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.team.dto.response.TeamMemberResponse
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import org.springframework.stereotype.Service

@Service
class TeamMemberServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
) : TeamMemberService {
    override fun getMyTeamMembers(userId: Long): List<TeamMemberResponse> {
        val user = teamMemberRepository.findByUserId(userId)

        val myTeamMembers = teamMemberRepository.findByTeamId(user.team.id!!)
            ?: throw ModelNotFoundException("myTeamMembers", user.team.id)

        return myTeamMembers.map { TeamMemberResponse.of(it) }

    }
}

