package com.teamsparta.tikitaka.domain.evaluation.repository

import com.querydsl.jpa.JPAExpressions.selectFrom
import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import com.teamsparta.tikitaka.domain.evaluation.model.QEvaluation
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EvaluationRepositoryImpl : CustomEvaluationRepository, QueryDslSupport() {
    override fun findEvaluationsBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Evaluation> {
        val evaluation = QEvaluation.evaluation

        return selectFrom(evaluation)
            .where(evaluation.createdAt.between(startDate, endDate))
            .fetch()
    }
}