package com.teamsparta.tikitaka.infra.security.config

import com.teamsparta.tikitaka.domain.evaluation.service.v3.EvaluationService
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class EvaluationTasklet(
    private val evaluationService: EvaluationService
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        evaluationService.calculateAndUpdateScores()
        return RepeatStatus.FINISHED
    }
}