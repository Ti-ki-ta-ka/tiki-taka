package com.teamsparta.tikitaka.domain.recruitment.service.v2.recruitmentapplication

import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.recruitment.dto.recruitmentapplication.RecruitmentApplicationResponse
import com.teamsparta.tikitaka.domain.recruitment.model.recruitmentapplication.RecruitmentApplication
import com.teamsparta.tikitaka.domain.recruitment.model.recruitmentapplication.ResponseStatus
import com.teamsparta.tikitaka.domain.recruitment.repository.RecruitmentRepository
import com.teamsparta.tikitaka.domain.recruitment.repository.recruitmentapplication.RecruitmentApplicationRepository
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RecruitmentApplicationServiceImpl(
    private val recruitmentRepository: RecruitmentRepository,
    private val recruitmentApplicationRepository: RecruitmentApplicationRepository,
    private val userRepository: UsersRepository
) : RecruitmentApplicationService {
    @Transactional
    override fun applyRecruitment(
        userId: Long, recruitmentId: Long, applicationId: Long
    ): RecruitmentApplicationResponse {
        val applicant = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("user", userId)
        if (applicant.teamStatus) throw IllegalStateException("you are already affiliated with another team.")

        val recruitment = recruitmentRepository.findByIdOrNull(recruitmentId) ?: throw ModelNotFoundException(
            "recruitment",
            recruitmentId
        )
        if (recruitment.closingStatus) throw IllegalStateException("This recruitment has already been closed.")
        val teamId = recruitment.teamId

        val newApplication = RecruitmentApplication.of(recruitment, teamId, userId, "WAITING")
        return RecruitmentApplicationResponse.from(recruitmentApplicationRepository.save(newApplication))
    }


    override fun cancelApplication(
        principal: UserPrincipal, recruitmentId: Long, applicationId: Long
    ): RecruitmentApplicationResponse {
        TODO()
    }
}