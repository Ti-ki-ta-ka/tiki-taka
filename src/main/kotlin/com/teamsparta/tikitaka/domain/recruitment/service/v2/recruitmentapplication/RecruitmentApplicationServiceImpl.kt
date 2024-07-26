package com.teamsparta.tikitaka.domain.recruitment.service.v2.recruitmentapplication

import com.teamsparta.tikitaka.domain.recruitment.dto.recruitmentapplication.RecruitmentApplicationResponse
import com.teamsparta.tikitaka.domain.recruitment.repository.RecruitmentRepository
import com.teamsparta.tikitaka.domain.recruitment.repository.recruitmentapplication.RecruitmentApplicationRepository
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import com.teamsparta.tikitaka.domain.team.service.v2.LeaderTeamService
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.stereotype.Service

@Service
class RecruitmentApplicationServiceImpl(
    private val recruitmentRepository: RecruitmentRepository,
    private val recruitmentApplicationRepository: RecruitmentApplicationRepository,
    private val teamRepository: TeamRepository,
    private val teamService: LeaderTeamService,
) : RecruitmentApplicationService {
    override fun applyRecruitment(
        userId: Long, recruitmentId: Long, applicationId: Long
    ): RecruitmentApplicationResponse {
        TODO()
    }

    override fun cancelApplication(
        principal: UserPrincipal, recruitmentId: Long, applicationId: Long
    ): RecruitmentApplicationResponse {
        TODO()
    }
}