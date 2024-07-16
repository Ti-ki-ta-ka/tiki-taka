package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplyRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplyResponse
import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication
import com.teamsparta.tikitaka.domain.matchApplication.repository.MatchApplicationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MatchApplicationServiceImpl
    (
    private val matchApplicationRepository: MatchApplicationRepository,
    private val matchRepository: MatchRepository
) : MatchApplicationService {
    @Transactional
    override fun applyMatch(request: CreateApplyRequest, matchId: Long): MatchApplyResponse {
        // 신청자가 리더나 서브 리더가 아닐 경우 신청 불가
        // MatchPost 중 동일한 MatchingDate 는 신청 불가 (다른 신청의 ApproveStatus 가 WAITING 인 경우)

        val matchPost = matchRepository.findByIdOrNull(matchId) ?: throw ModelNotFoundException("match", matchId)
        val (teamId) = request
        return matchApplicationRepository.save(MatchApplication.of(matchPost, teamId))
            .let { MatchApplyResponse.from(it) }
    }

    @Transactional
    override fun deleteMatch(userId: Long, applicationId: Long) {
        // 사용자 인증여부 조회, 사용자 미인증 시 예외처리 진행
        // 팀 맴버 Role 의 경우, 신청자 또는 해당 팀의 리더만 가능
        // SoftDelete 진행

        TODO()
    }


}