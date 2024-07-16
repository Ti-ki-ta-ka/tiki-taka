package com.teamsparta.tikitaka.domain.matchApplication.controller.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplyRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplyResponse
import com.teamsparta.tikitaka.domain.matchApplication.service.v1.MatchApplicationService
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/matches")
@RestController
class MatchApplicationController(
    private val matchApplicationService: MatchApplicationService
) {
    @PostMapping("/{matchId}/match-applications")
    fun applyMatch(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable matchId: Long,
        @RequestBody request: CreateApplyRequest
    ): ResponseEntity<MatchApplyResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchApplicationService.applyMatch(principal.id, request, matchId))
    }

    @DeleteMapping("/{matchId}/match-applications/{applicationId}")
    fun deleteMatch(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable applicationId: Long,
    ): ResponseEntity<Unit> {
        matchApplicationService.deleteMatch(principal, applicationId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}