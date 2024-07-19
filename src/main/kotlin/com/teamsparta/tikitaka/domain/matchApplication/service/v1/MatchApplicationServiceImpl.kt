package com.teamsparta.tikitaka.domain.matchApplication.service.v1

import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.common.exception.TeamAlreadyAppliedException
import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import com.teamsparta.tikitaka.domain.matchApplication.dto.CreateApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.dto.MatchApplicationResponse
import com.teamsparta.tikitaka.domain.matchApplication.dto.ReplyApplicationRequest
import com.teamsparta.tikitaka.domain.matchApplication.model.ApproveStatus
import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication
import com.teamsparta.tikitaka.domain.matchApplication.repository.MatchApplicationRepository
import com.teamsparta.tikitaka.domain.team.model.teamMember.TeamRole
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class MatchApplicationServiceImpl
    (
    private val matchApplicationRepository: MatchApplicationRepository,
    private val matchRepository: MatchRepository,
    private val usersRepository: UsersRepository,
    private val teamMemberRepository: TeamMemberRepository
) : MatchApplicationService {
    @Transactional
    override fun applyMatch(userId: Long, request: CreateApplicationRequest, matchId: Long): MatchApplicationResponse {
        usersRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val matchPost = matchRepository.findByIdOrNull(matchId) ?: throw ModelNotFoundException("match", matchId)
        val (teamId) = request

        val matchDate = matchPost.matchDate.toLocalDate()

        val existingApplications = matchApplicationRepository.findByTeamIdAndMatchDate(teamId, matchDate)
        if (existingApplications.any { it.approveStatus == ApproveStatus.WAITING || it.approveStatus == ApproveStatus.APPROVE }) {
            throw TeamAlreadyAppliedException("Your team already has a pending or approved application for the same match date.")
        }

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
        matchApply.approveStatus = ApproveStatus.CANCELLED

    }

    @Transactional
    override fun replyMatchApplication(
        userId: Long,
        applicationId: Long,
        request: ReplyApplicationRequest
    ): MatchApplicationResponse {
        usersRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val (approveStatus) = request

        // 현재의 applicationId의 approveStatus가 CANCELLED일 경우로 수정
        if (approveStatus == ApproveStatus.CANCELLED.toString()) {
            throw IllegalStateException("Cannot modify application with status CANCELLED")
        }


        //ApplicationId가 존재하는지 여부를 체크
        val matchApply = matchApplicationRepository.findByIdOrNull(applicationId) ?: throw ModelNotFoundException(
            "MatchApplication",
            applicationId
        )

        //조회한 신청이 어떤 매치인지 조회
        val match = matchApply.matchPost
        val matchUserId = match.userId

        //현재 해당 API에 접근을 시도하는 사용자가 PostMatch한 팀의 소속인지 체크
        val userTeamMember = teamMemberRepository.findByUserIdAndTeamId(userId, match.teamId)
            ?: throw ModelNotFoundException("TeamMember", userId)

        // 만약 해당  API에 접근을 시도한 사용자가 MatchPost한 사용자와 다를경우, MatchPost한 사용자가 속한 팀의 리더가 맞는지 체크
        if (userId != matchUserId) {
            val teamLeader = teamMemberRepository.findByUserId(userId)
            if (teamLeader.teamRole != TeamRole.LEADER && userTeamMember.teamRole != TeamRole.LEADER) {
                throw AccessDeniedException("Only the author or the team leader can respond to this application")
            }
        }

        matchApply.approveStatus = ApproveStatus.fromString(approveStatus)
        return MatchApplicationResponse.from(matchApply)
    }
}
