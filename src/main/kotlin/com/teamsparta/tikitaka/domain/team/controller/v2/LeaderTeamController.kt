package com.teamsparta.tikitaka.domain.team.controller.v2

import com.teamsparta.tikitaka.domain.team.dto.request.ReassignRoleRequest
import com.teamsparta.tikitaka.domain.team.dto.response.DelegateLeaderResponse
import com.teamsparta.tikitaka.domain.team.dto.response.ReassignRoleResponse
import com.teamsparta.tikitaka.domain.team.dto.response.RemoveMemberResopnse
import com.teamsparta.tikitaka.domain.team.model.teammember.TeamRole
import com.teamsparta.tikitaka.domain.team.service.v2.LeaderTeamService
import com.teamsparta.tikitaka.infra.security.CustomPreAuthorize
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/leader/team-members/{team-member-id}")
class LeaderTeamController(
    private val preAuthorize: CustomPreAuthorize,
    private val leaderTeamService: LeaderTeamService,
) {

    @PutMapping("/reassign")
    fun reassignRole(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable(name = "team-member-id") teamMemberId: Long,
        @RequestBody request: ReassignRoleRequest,
    ): ResponseEntity<ReassignRoleResponse> {
        return preAuthorize.hasAnyRole(principal, setOf(TeamRole.LEADER)) {
            ResponseEntity.status(HttpStatus.OK)
                .body(leaderTeamService.reassignRole(principal, teamMemberId, request))
        }
    }

    @PutMapping("/delegate")
    fun delegateLeader(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable(name = "team-member-id") teamMemberId: Long,
    ): ResponseEntity<DelegateLeaderResponse> {
        return preAuthorize.hasAnyRole(principal, setOf(TeamRole.LEADER)) {
            ResponseEntity.status(HttpStatus.OK)
                .body(leaderTeamService.delegateLeader(principal, teamMemberId))
        }

    }

    @DeleteMapping()
    fun removeMember(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable(name = "team-member-id") teamMemberId: Long,
    ): ResponseEntity<RemoveMemberResopnse> {
        return preAuthorize.hasAnyRole(principal, setOf(TeamRole.LEADER)) {
            ResponseEntity.status(HttpStatus.OK)
                .body(leaderTeamService.removeMember(principal, teamMemberId))
        }
    }
}


