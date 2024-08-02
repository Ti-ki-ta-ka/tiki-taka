package com.teamsparta.tikitaka.domain.evaluation.repository

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import com.teamsparta.tikitaka.domain.evaluation.model.QEvaluation
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import java.time.LocalDateTime

class EvaluationRepositoryImpl : CustomEvaluationRepository, QueryDslSupport() {
    override fun findEvaluationsForTeamFromLast90Days(teamId: Long, startDate: LocalDateTime): List<Evaluation> {
        val evaluation = QEvaluation.evaluation

        return queryFactory.selectFrom(evaluation)
            .where(
                evaluation.evaluateeTeamId.eq(teamId)
                    .and(evaluation.createdAt.goe(startDate))
                    .and(evaluation.evaluationStatus.isTrue)
            )
            .fetch()
    }
}