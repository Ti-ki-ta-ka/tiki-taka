@file:JvmName("MatchApplicationRepositoryKt")

package com.teamsparta.tikitaka.domain.match.repository.matchapplication

import com.querydsl.core.BooleanBuilder
import com.teamsparta.tikitaka.domain.match.model.QMatch
import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication
import com.teamsparta.tikitaka.domain.matchApplication.model.QMatchApplication
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import java.time.LocalDate

class MatchApplicationRepositoryImpl : CustomMatchApplicationRepository, QueryDslSupport() {

    private val qMatchApplication = QMatchApplication.matchApplication
    private val qMatch = QMatch.match

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
}
