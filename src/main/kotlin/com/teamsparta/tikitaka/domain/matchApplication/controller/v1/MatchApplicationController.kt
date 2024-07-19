package com.teamsparta.tikitaka.domain.matchApplication.controller.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplicationResponse
import com.teamsparta.tikitaka.domain.matchApplication.dto.ReplyApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.service.v1.MatchApplicationService
import com.teamsparta.tikitaka.domain.team.model.teamMember.TeamRole
import com.teamsparta.tikitaka.infra.security.CustomPreAuthorize
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/matches")
@RestController
class MatchApplicationController(
    private val matchApplicationService: MatchApplicationService,
    private val preAuthorize: CustomPreAuthorize,
) {
    @PostMapping("/{matchId}/match-applications")
    fun applyMatch(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable matchId: Long,
        @RequestBody request: CreateApplicationRequest
    ): ResponseEntity<MatchApplicationResponse> {
        return preAuthorize.hasAnyRole(principal, setOf(TeamRole.LEADER, TeamRole.SUB_LEADER)) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(matchApplicationService.applyMatch(principal.id, request, matchId))
        }
    }

    @DeleteMapping("/{matchId}/match-applications/{applicationId}")
    fun deleteMatchApplication(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable applicationId: Long,
    ): ResponseEntity<Unit> {
        matchApplicationService.deleteMatchApplication(principal, applicationId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/{matchId}/match-applications/{applicationId}")
    fun replyMatchApplication(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable applicationId: Long,
        @RequestBody request: ReplyApplicationRequest
    ): ResponseEntity<MatchApplicationResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(matchApplicationService.replyMatchApplication(principal.id, applicationId, request))
    }
}