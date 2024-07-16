package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplicationResponse
import com.teamsparta.tikitaka.domain.matchApplication.dto.ReplyApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.model.ApproveStatus
import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication
import com.teamsparta.tikitaka.domain.matchApplication.repository.MatchApplicationRepository
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MatchApplicationServiceImpl
    (
    private val matchApplicationRepository: MatchApplicationRepository,
    private val matchRepository: MatchRepository,
    private val usersRepository: UsersRepository,
) : MatchApplicationService {
    @Transactional
    override fun applyMatch(userId: Long, request: CreateApplicationRequest, matchId: Long): MatchApplicationResponse {
        // 신청자가 리더나 작성자가 아닐 경우 신청 불가
        // MatchPost 중 동일한 MatchingDate 는 신청 불가 (다른 신청의 ApproveStatus 가 WAITING 인 경우)
        usersRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val matchPost = matchRepository.findByIdOrNull(matchId) ?: throw ModelNotFoundException("match", matchId)
        val (teamId) = request
        return matchApplicationRepository.save(MatchApplication.of(matchPost, teamId, userId))
            .let { MatchApplicationResponse.from(it) }
    }

    @Transactional
    override fun deleteMatchApplication(principal: UserPrincipal, applicationId: Long) {
        val matchApply = matchApplicationRepository.findByIdOrNull(applicationId) ?: throw ModelNotFoundException(
            "match",
            applicationId
        )
        if (matchApply.applyUserId != principal.id && !principal.authorities.contains(SimpleGrantedAuthority("ROLE_LEADER"))) throw AccessDeniedException(
            "You do not have permission to delete."
        )
        matchApply.delete()
    }

    @Transactional
    override fun replyMatchApplication(
        userId: Long,
        applicationId: Long,
        request: ReplyApplicationRequest
    ): MatchApplicationResponse {
        // MatchRepository 에서 PostMatch 한 작성자와 답변하려는 작성자가 동일한지 확인하는 로직
        val (approveStatus) = request
        if (approveStatus == ApproveStatus.CANCELLED.toString()) {
            throw IllegalStateException("Cannot modify application with status CANCELLED")
        }
        val matchApply = matchApplicationRepository.findByIdOrNull(applicationId) ?: throw ModelNotFoundException(
            "match",
            applicationId
        )
        matchApply.approveStatus = ApproveStatus.fromString(approveStatus)
        return MatchApplicationResponse.from(matchApply)
    }
}
