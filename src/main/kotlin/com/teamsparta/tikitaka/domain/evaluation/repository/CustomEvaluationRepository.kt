package com.teamsparta.tikitaka.domain.evaluation.repository

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import java.time.LocalDateTime

interface CustomEvaluationRepository {
    fun findEvaluationsBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Evaluation>
}