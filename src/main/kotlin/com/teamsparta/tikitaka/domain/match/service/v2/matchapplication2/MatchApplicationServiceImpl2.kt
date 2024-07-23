package com.teamsparta.tikitaka.domain.match.service.v2.matchapplication2

import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.common.exception.TeamAlreadyAppliedException
import com.teamsparta.tikitaka.domain.match.dto.matchapplication.*
import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.match.model.matchapplication.ApproveStatus
import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication
import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import com.teamsparta.tikitaka.domain.match.repository.matchapplication.MatchApplicationRepository
import com.teamsparta.tikitaka.domain.team.model.teammember.TeamRole
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MatchApplicationServiceImpl2
    (
    private val matchApplicationRepository: MatchApplicationRepository,
    private val matchRepository: MatchRepository,
    private val usersRepository: UsersRepository,
    private val teamMemberRepository: TeamMemberRepository
) : MatchApplicationService2 {
    @Transactional
    override fun applyMatch(userId: Long, request: CreateApplicationRequest, matchId: Long): MatchApplicationResponse {
        usersRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val matchPost = matchRepository.findByIdOrNull(matchId) ?: throw ModelNotFoundException("Match", matchId)
        val (teamId) = request

        validateMatchAvailability(matchPost, teamId)
        validateExistingApplications(teamId, matchId, matchPost.matchDate.toLocalDate())

        val newApplication = MatchApplication.of(matchPost, teamId, userId)
        return MatchApplicationResponse.from(matchApplicationRepository.save(newApplication))
    }


    @Transactional
    override fun cancelMatchApplication(principal: UserPrincipal, matchId: Long, applicationId: Long) {
        matchRepository.findByIdOrNull(matchId) ?: throw ModelNotFoundException("Match", matchId)
        val matchApply = matchApplicationRepository.findByIdOrNull(applicationId)
            ?: throw ModelNotFoundException("match", applicationId)

        validatePermission(principal, matchApply)

        matchApply.approveStatus = ApproveStatus.CANCELLED
    }

    @Transactional
    override fun replyMatchApplication(
        userId: Long,
        matchId: Long,
        applicationId: Long,
        request: ReplyApplicationRequest
    ): MatchApplicationResponse {
        usersRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val (approveStatus) = request
        val matchPost = matchRepository.findByIdOrNull(matchId) ?: throw ModelNotFoundException("Match", matchId)
        val matchApply = matchApplicationRepository.findByIdOrNull(applicationId) ?: throw ModelNotFoundException(
            "MatchApplication",
            applicationId
        )

        if (matchApply.approveStatus == ApproveStatus.CANCELLED) {
            throw IllegalStateException("Cannot modify application with status CANCELLED")
        }

        val matchUserId = matchPost.userId

        val userTeamMember = teamMemberRepository.findByUserIdAndTeamId(userId, matchPost.teamId)
            ?: throw ModelNotFoundException("TeamMember", userId)

        if (userId != matchUserId) {
            if (userTeamMember.teamRole != TeamRole.LEADER) {
                throw AccessDeniedException("Only the author or the team leader can respond to this application")
            }
        }

        if (approveStatus == ApproveStatus.CANCELLED.toString()) {
            throw IllegalStateException("Only the applicant can cancel the match application.")
        }

        matchApply.approveStatus = ApproveStatus.fromString(approveStatus)

        if (matchApply.approveStatus == ApproveStatus.APPROVE) {
            rejectOtherApplications(matchId, applicationId)
        }

        matchPost.matchStatus = true
        return MatchApplicationResponse.from(matchApply)
    }

    override fun getMyApplications(
        request: MyApplicationRequest
    ): List<MyApplicationsResponse> {
        return matchApplicationRepository.findByApplyTeamId(request.teamId)
            .map { application -> MyApplicationsResponse.from(application) }
    }

    private fun validateMatchAvailability(matchPost: Match, teamId: Long) {
        if (matchPost.matchStatus) {
            throw IllegalStateException("This match is already closed for applications.")
        }
        if (matchPost.teamId == teamId) {
            throw IllegalArgumentException("Team cannot apply to its own posted match.")
        }
    }

    private fun validateExistingApplications(teamId: Long, matchId: Long, matchDate: LocalDate) {
        val sameTeamApplications = matchApplicationRepository.findByApplyTeamIdAndMatchPostId(teamId, matchId)
        val dateApplications = matchApplicationRepository.findByTeamIdAndMatchDate(teamId, matchDate)

        if (sameTeamApplications.any { it.approveStatus == ApproveStatus.WAITING || it.approveStatus == ApproveStatus.APPROVE }) {
            throw TeamAlreadyAppliedException("Your team already has a pending or approved application for this match.")
        }
        if (dateApplications.any { it.approveStatus == ApproveStatus.WAITING || it.approveStatus == ApproveStatus.APPROVE }) {
            throw TeamAlreadyAppliedException("Your team already has a pending or approved application for the same date.")
        }
    }

    private fun validatePermission(principal: UserPrincipal, matchApply: MatchApplication) {
        if (matchApply.applyUserId != principal.id && !principal.authorities.contains(SimpleGrantedAuthority("ROLE_LEADER"))) {
            throw AccessDeniedException("You do not have permission to delete.")
        }
        when (matchApply.approveStatus) {
            ApproveStatus.REJECT, ApproveStatus.APPROVE -> throw IllegalStateException("You cannot cancel an application that has already been approved or rejected.")
            ApproveStatus.CANCELLED -> throw IllegalStateException("You already canceled this application.")
            else -> {}
        }
    }

    private fun rejectOtherApplications(matchId: Long, approvedApplicationId: Long) {
        val otherApplications =
            matchApplicationRepository.findByMatchPostIdAndApproveStatus(matchId, ApproveStatus.WAITING)
        for (application in otherApplications) {
            if (application.id != approvedApplicationId) {
                application.approveStatus = ApproveStatus.REJECT
            }
        }
    }
}

