package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.team.dto.response.TeamMemberResponse
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.stereotype.Service

@Service
class TeamMemberServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
) : TeamMemberService {
    override fun getMyTeamMembers(principal: UserPrincipal): List<TeamMemberResponse> {
        val leader = teamMemberRepository.findByUserId(principal.id)

        val myTeamMembers = teamMemberRepository.findByTeamId(leader.team.id!!)
            ?: throw ModelNotFoundException("myTeamMembers", leader.team.id)

        return myTeamMembers.map { TeamMemberResponse.of(it) }

    }
}

