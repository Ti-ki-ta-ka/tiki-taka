package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.team.dto.response.TeamMemberResponse
import com.teamsparta.tikitaka.infra.security.UserPrincipal

interface TeamMemberService {
    fun getMyTeamMembers(principal: UserPrincipal): List<TeamMemberResponse>
}