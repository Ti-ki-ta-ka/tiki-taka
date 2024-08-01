package com.teamsparta.tikitaka.domain.evaluation.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EvaluationScheduler(
    private val successMatchRepository: SuccessMatchRepository,
    private val evaluationService: EvaluationService,
) {

    @Scheduled(fixedRate = 30000)
    fun createEvaluations() {
        val now = LocalDateTime.now()
        val twoHoursAgo = now.minusHours(2)
        val matches = successMatchRepository.findByMatchDateBeforeAndEvaluationCreatedFalse(twoHoursAgo)

        matches.forEach { match ->
            evaluationService.createEvaluationsForMatch(match)
        }
    }
}