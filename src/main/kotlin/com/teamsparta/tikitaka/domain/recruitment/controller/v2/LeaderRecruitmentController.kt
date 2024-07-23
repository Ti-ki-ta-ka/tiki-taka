package com.teamsparta.tikitaka.domain.recruitment.controller.v2

import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentRequest
import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentResponse
import com.teamsparta.tikitaka.domain.recruitment.dto.RecruitmentResponse
import com.teamsparta.tikitaka.domain.recruitment.dto.UpdateRecruitmentRequest
import com.teamsparta.tikitaka.domain.recruitment.service.v2.LeaderRecruitmentService
import com.teamsparta.tikitaka.domain.team.model.teammember.TeamRole
import com.teamsparta.tikitaka.infra.security.CustomPreAuthorize
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/leader/recruitments")
class LeaderRecruitmentController(
    private val preAuthorize: CustomPreAuthorize,
    private val leaderRecruitmentService: LeaderRecruitmentService,
) {

    @PostMapping()
    fun postRecruitment(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: PostRecruitmentRequest
    ): ResponseEntity<PostRecruitmentResponse> {
        return preAuthorize.hasAnyRole(principal, setOf(TeamRole.LEADER)) {
            ResponseEntity.status(HttpStatus.OK)
                .body(leaderRecruitmentService.postRecruitment(principal, request))
        }
    }

    @PutMapping("{recruitment-id}")
    fun updateRecruitmentPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable(name = "recruitment-id") recruitmentId: Long,
        @RequestBody request: UpdateRecruitmentRequest
    ): ResponseEntity<RecruitmentResponse> {
        return preAuthorize.hasAnyRole(principal, setOf(TeamRole.LEADER)) {
            ResponseEntity.status(HttpStatus.OK)
                .body(leaderRecruitmentService.updateRecruitmentPost(principal.id, recruitmentId, request))
        }
    }
}
