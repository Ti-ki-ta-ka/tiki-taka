package com.teamsparta.tikitaka.domain.team.repository.teamMember

import com.teamsparta.tikitaka.domain.team.model.teamMember.TeamMember
import org.springframework.data.jpa.repository.JpaRepository

interface TeamMemberRepository : JpaRepository<TeamMember, Long> {
    fun findByUserId(userId: Long): TeamMember
    fun findByUserIdAndTeamId(userId: Long, teamId: Long): TeamMember?
}