package com.teamsparta.tikitaka.domain.evaluation.repository

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import com.teamsparta.tikitaka.domain.evaluation.model.QEvaluation
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import java.time.LocalDateTime

class EvaluationRepositoryImpl : CustomEvaluationRepository, QueryDslSupport() {
    override fun findEvaluationsForTeamFromLast90Days(
        teamId: Long,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Evaluation> {
        val evaluation = QEvaluation.evaluation

        return queryFactory.selectFrom(evaluation)
            .where(
                evaluation.evaluateeTeamId.eq(teamId)
                    .and(evaluation.createdAt.between(startDate, endDate))
                    .and(evaluation.evaluationStatus.isTrue)
            )
            .fetch()
    }

    override fun softDeleteOldEvaluations(threshold: LocalDateTime, now: LocalDateTime) {
        val evaluation = QEvaluation.evaluation

        queryFactory.update(evaluation)
            .set(evaluation.deletedAt, now)
            .where(
                evaluation.createdAt.lt(threshold)
                    .and(evaluation.deletedAt.isNull)
            )
            .execute()
    }
}