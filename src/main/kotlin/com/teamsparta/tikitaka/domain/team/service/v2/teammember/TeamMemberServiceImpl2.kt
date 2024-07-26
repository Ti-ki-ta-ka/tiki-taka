package com.teamsparta.tikitaka.domain.team.service.v2.teammember

import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.team.dto.response.RemoveMemberResopnse
import com.teamsparta.tikitaka.domain.team.dto.response.TeamMemberResponse
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import org.springframework.stereotype.Service

@Service
class TeamMemberServiceImpl2(
    private val teamMemberRepository: TeamMemberRepository,
) : TeamMemberService2 {
    override fun getMyTeamMembers(userId: Long): List<TeamMemberResponse> {
        val user = teamMemberRepository.findByUserId(userId)

        val myTeamMembers = teamMemberRepository.findByTeamId(user.team.id!!)
            ?: throw ModelNotFoundException("myTeamMembers", user.team.id)

        return myTeamMembers.map { TeamMemberResponse.of(it) }
    }

    override fun leaveTeam(userId: Long, teamMemberId: Long): RemoveMemberResopnse {
        TODO()
    }
}

