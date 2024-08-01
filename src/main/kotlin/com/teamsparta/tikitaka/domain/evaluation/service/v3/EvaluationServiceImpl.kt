package com.teamsparta.tikitaka.domain.evaluation.service.v3

import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.evaluation.dto.EvaluationRequest
import com.teamsparta.tikitaka.domain.evaluation.dto.EvaluationResponse
import com.teamsparta.tikitaka.domain.evaluation.repository.EvaluationRepository
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EvaluationServiceImpl(
    private val evaluationRepository: EvaluationRepository,
    private val usersRepository: UsersRepository,
) : EvaluationService {

    @Transactional
    override fun evaluate(
        evaluationId: Long,
        principal: UserPrincipal,
        request: EvaluationRequest
    ): EvaluationResponse {
        val user = usersRepository.findByIdOrNull(principal.id) ?: throw ModelNotFoundException("USER", principal.id)
        val evaluation = evaluationRepository.findByIdOrNull(evaluationId) ?: throw ModelNotFoundException(
            "EVALUATION",
            evaluationId
        )
        val evaluator = evaluation.evaluatorId
        if (user.id != evaluator) {
            throw AccessDeniedException("평가 권한이 없습니다")
        }
        if (evaluation.evaluationStatus) {
            throw IllegalArgumentException("이미 평가한 팀 입니다")
        }
        evaluation.updateEvaluation(request)
        evaluation.evaluationStatus = true
        return EvaluationResponse.from(evaluation)
    }
}