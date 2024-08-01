package com.teamsparta.tikitaka.domain.evaluation.service

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import com.teamsparta.tikitaka.domain.evaluation.repository.EvaluationRepository
import com.teamsparta.tikitaka.domain.match.model.SuccessMatch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class EvaluationServiceImpl(
    private val evaluationRepository: EvaluationRepository,
    private val successMatchRepository: SuccessMatchRepository,
) : EvaluationService {
    @Transactional
    override fun createEvaluationsForMatch(match: SuccessMatch) {
        val hostTeamEvaluation = Evaluation(
            evaluatorTeamId = match.hostTeamId,
            evaluateeTeamId = match.guestTeamId,
            evaluatorId = match.guestId,
            createdAt = LocalDateTime.now(),
        )
        val guestTeamEvaluation = Evaluation(
            evaluatorTeamId = match.guestTeamId,
            evaluateeTeamId = match.hostTeamId,
            evaluatorId = match.hostId,
            createdAt = LocalDateTime.now(),
        )

        evaluationRepository.save(hostTeamEvaluation)
        evaluationRepository.save(guestTeamEvaluation)

        match.evaluationCreatedTrue()
        successMatchRepository.save(match)


    }
}