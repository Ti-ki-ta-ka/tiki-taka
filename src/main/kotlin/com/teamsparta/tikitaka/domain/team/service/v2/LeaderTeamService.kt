package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.team.dto.request.DelegateLeaderRequest
import com.teamsparta.tikitaka.domain.team.dto.request.ReassignRoleRequest
import com.teamsparta.tikitaka.domain.team.dto.request.RemoveMemberRequest
import com.teamsparta.tikitaka.domain.team.dto.response.DelegateLeaderResponse
import com.teamsparta.tikitaka.domain.team.dto.response.ReassignRoleResponse
import com.teamsparta.tikitaka.domain.team.dto.response.RemoveMemberResopnse
import com.teamsparta.tikitaka.infra.security.UserPrincipal


interface LeaderTeamService {

    fun reassignRole(principal: UserPrincipal, request: ReassignRoleRequest): ReassignRoleResponse
    fun delegateLeader(principal: UserPrincipal, request: DelegateLeaderRequest): DelegateLeaderResponse
    fun removeMember(principal: UserPrincipal, request: RemoveMemberRequest): RemoveMemberResopnse


}
