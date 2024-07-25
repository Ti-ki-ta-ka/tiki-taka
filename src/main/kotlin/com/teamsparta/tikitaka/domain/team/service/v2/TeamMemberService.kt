package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.team.dto.response.TeamMemberResponse

interface TeamMemberService {
    fun getMyTeamMembers(userId: Long): List<TeamMemberResponse>
}