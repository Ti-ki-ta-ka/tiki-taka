@file:JvmName("MatchApplicationRepositoryKt")

package com.teamsparta.tikitaka.domain.match.repository.matchapplication

import com.querydsl.core.BooleanBuilder
import com.teamsparta.tikitaka.domain.match.dto.matchapplication.MatchApplicationsByIdResponse
import com.teamsparta.tikitaka.domain.match.model.QMatch.match
import com.teamsparta.tikitaka.domain.match.model.matchapplication.ApproveStatus
import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication
import com.teamsparta.tikitaka.domain.match.model.matchapplication.QMatchApplication.matchApplication
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.LocalDate

class MatchApplicationRepositoryImpl : CustomMatchApplicationRepository, QueryDslSupport() {

    private val qMatchApplication = matchApplication
    private val qMatch = match

    override fun findByTeamIdAndMatchDate(teamId: Long, matchDate: LocalDate): List<MatchApplication> {

        return queryFactory.selectFrom(qMatchApplication)
            .join(qMatchApplication.matchPost, qMatch)
            .where(
                qMatchApplication.applyTeamId.eq(teamId)
                    .and(qMatch.matchDate.year().eq(matchDate.year))
                    .and(qMatch.matchDate.month().eq(matchDate.monthValue))
                    .and(qMatch.matchDate.dayOfMonth().eq(matchDate.dayOfMonth))
            )
            .fetch()
    }

    override fun findByApplyTeamId(applyTeamId: Long): List<MatchApplication> {

        val whereClause = BooleanBuilder()
        applyTeamId.let { whereClause.and(qMatchApplication.applyTeamId.eq(applyTeamId)) }

        val content = queryFactory
            .selectFrom(qMatchApplication)
            .leftJoin(qMatchApplication.matchPost, qMatch)
            .fetchJoin()
            .where(whereClause)
            .orderBy(qMatchApplication.applyTeamId.desc())
            .fetch()

        return content
    }

    override fun findApplicationsByMatchId(
        pageable: Pageable,
        matchId: Long,
        approveStatus: String?
    ): Page<MatchApplicationsByIdResponse> {
        val whereClause = BooleanBuilder()
        approveStatus?.let { whereClause.and(matchApplication.approveStatus.eq(ApproveStatus.fromString(it))) }
        matchId.let { whereClause.and(matchApplication.matchPost.id.eq(it)) }

        val totalCount =
            queryFactory.select(matchApplication.count()).from(matchApplication).where(whereClause)
                .fetchOne() ?: 0L

        val applications =
            queryFactory.selectFrom(matchApplication).leftJoin(matchApplication.matchPost, match)
                .fetchJoin()
                .where(whereClause).orderBy(matchApplication.createdAt.asc()).offset(pageable.offset)
                .limit(pageable.pageSize.toLong()).fetch()

        val matchApplicationResponse = applications.map { application ->
            MatchApplicationsByIdResponse(
                id = application.id!!,
                applyUserId = application.applyUserId,
                applyTeamId = application.applyTeamId,
                approveStatus = application.approveStatus.toString(),
                createdAt = application.createdAt
            )
        }
        return PageImpl(matchApplicationResponse, pageable, totalCount)
    }
}
