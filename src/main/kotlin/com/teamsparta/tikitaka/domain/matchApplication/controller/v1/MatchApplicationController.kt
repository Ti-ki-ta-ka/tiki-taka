package com.teamsparta.tikitaka.domain.matchApplication.controller.v1

import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplyRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplyResponse
import com.teamsparta.tikitaka.domain.matchApplication.service.v1.MatchApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/matches")
@RestController
class MatchApplicationController(
    private val matchApplicationService: MatchApplicationService
) {
    @PostMapping("/{matchId}/match-applications")
    fun applyMatch(
        // Users 구현 이후, 인증 부분 추가 필요
        @PathVariable matchId: Long,
        @RequestBody request: CreateApplyRequest
    ): ResponseEntity<MatchApplyResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchApplicationService.applyMatch(request, matchId))
    }
}