package com.teamsparta.tikitaka.domain.evaluation.service

import com.teamsparta.tikitaka.domain.match.model.SuccessMatch

interface EvaluationService {
    fun createEvaluationsForMatch(match: SuccessMatch)
}