package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.team.dto.request.DelegateLeaderRequest
import com.teamsparta.tikitaka.domain.team.dto.request.ReassignRoleRequest
import com.teamsparta.tikitaka.domain.team.dto.request.RemoveMemberRequest
import com.teamsparta.tikitaka.domain.team.dto.response.DelegateLeaderResponse
import com.teamsparta.tikitaka.domain.team.dto.response.ReassignRoleResponse
import com.teamsparta.tikitaka.domain.team.dto.response.RemoveMemberResopnse
import com.teamsparta.tikitaka.domain.team.model.teammember.TeamRole
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LeaderTeamServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
) : LeaderTeamService {

    override fun reassignRole(
        principal: UserPrincipal,
        request: ReassignRoleRequest,
    ): ReassignRoleResponse {
        val teamMember = teamMemberRepository.findByIdOrNull(request.teamMemberId)
            ?: throw ModelNotFoundException("team member", request.teamMemberId)


        when (request.role) {
            TeamRole.SUB_LEADER -> {
                if (teamMember.teamRole == TeamRole.MEMBER) {
                    teamMember.teamRole = TeamRole.SUB_LEADER
                } else {
                    throw IllegalArgumentException(" The team member is already a sub-leader ")
                }
            }

            TeamRole.MEMBER -> {
                if (teamMember.teamRole == TeamRole.SUB_LEADER) {
                    teamMember.teamRole = TeamRole.MEMBER
                } else {
                    throw IllegalArgumentException(" The team member is already a member ")
                }
            }

            else -> throw IllegalArgumentException("Invalid role for reassignment: ${request.role}")
        }

        teamMemberRepository.save(teamMember)

        return ReassignRoleResponse.from(teamMember)
    }


    override fun delegateLeader(principal: UserPrincipal, request: DelegateLeaderRequest): DelegateLeaderResponse {
        TODO("Not yet implemented")
    }

    override fun removeMember(principal: UserPrincipal, request: RemoveMemberRequest): RemoveMemberResopnse {
        TODO("Not yet implemented")
    }
}
