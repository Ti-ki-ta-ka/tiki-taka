package com.teamsparta.tikitaka.domain.recruitment.service.v2

import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentRequest
import com.teamsparta.tikitaka.domain.recruitment.dto.PostRecruitmentResponse
import com.teamsparta.tikitaka.domain.recruitment.model.Recruitment
import com.teamsparta.tikitaka.domain.recruitment.repository.RecruitmentRepository
import com.teamsparta.tikitaka.domain.team.model.teammember.TeamRole
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.stereotype.Service

@Service
class LeaderRecruitmentServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
    private val recruitmentRepository: RecruitmentRepository,
) : LeaderRecruitmentService {
    override fun postRecruitment(principal: UserPrincipal, request: PostRecruitmentRequest): PostRecruitmentResponse {

        val leader = teamMemberRepository.findByUserId(principal.id)
            ?: throw ModelNotFoundException("leader", principal.id)

        if (leader.teamRole != TeamRole.LEADER) {
            throw IllegalArgumentException("Only the current leader can reassign roles")
        }

        val recruitment = recruitmentRepository.save(
            Recruitment.of(
                teamId = leader.team.id!!,
                userId = principal.id,
                recruitType = request.recruitType,
                quantity = request.quantity,
                content = request.content,
                closingStatus = false,
            )
        )

        return PostRecruitmentResponse.from(recruitment)

    }
}