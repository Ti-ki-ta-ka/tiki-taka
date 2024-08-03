package com.teamsparta.tikitaka.domain.evaluation.repository

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import java.time.LocalDateTime

interface CustomEvaluationRepository {
    fun findEvaluationsForTeamFromLast90Days(
        teamId: Long,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Evaluation>
}