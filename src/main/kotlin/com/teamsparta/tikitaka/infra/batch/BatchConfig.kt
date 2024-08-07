package com.teamsparta.tikitaka.infra.batch

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import com.teamsparta.tikitaka.domain.evaluation.repository.EvaluationRepository
import com.teamsparta.tikitaka.domain.team.model.Team
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

@Configuration
class BatchConfig(
    private val evaluationRepository: EvaluationRepository,
    private val teamRepository: TeamRepository,
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager
) {

    @Bean
    fun teamEvaluationJob(teamEvaluationStep: Step): Job {
        return JobBuilder("teamEvaluationJob", jobRepository).start(teamEvaluationStep).build()
    }

    @Bean
    fun teamEvaluationStep(): Step {
        return StepBuilder("teamEvaluationStep", jobRepository).chunk<Evaluation, Team>(10, transactionManager)
            .reader(evaluationItemReader()).processor(evaluationItemProcessor()).writer(teamItemWriter()).build()
    }

    @StepScope
    @Bean
    fun evaluationItemReader(): ItemReader<Evaluation> {
        val startDateTime = LocalDateTime.now().minusDays(91)
        val endDateTime = LocalDateTime.now().minusDays(1)

        val evaluations = evaluationRepository.findEvaluationsBetween(startDateTime, endDateTime)

        return ListItemReader(evaluations)
    }

    @StepScope
    @Bean
    fun evaluationItemProcessor(): ItemProcessor<Evaluation, Team> {
        return ItemProcessor { evaluation ->
            val teamId = evaluation.evaluateeTeamId

            val team = teamRepository.findById(teamId).orElseThrow {
                IllegalArgumentException("Team not found for teamId: $teamId")
            }

            val evaluations = evaluationRepository.findEvaluationsBetween(
                LocalDateTime.now().minusDays(91), LocalDateTime.now().minusDays(1)
            ).filter { it.evaluateeTeamId == teamId }

            team.mannerScore = 0
            team.tierScore = 0
            team.attendanceScore = 0

            evaluations.forEach { eval ->
                team.mannerScore += eval.mannerScore
                team.tierScore += eval.skillScore
                team.attendanceScore += eval.attendanceScore
            }

            team
        }
    }

    @StepScope
    @Bean
    fun teamItemWriter(): ItemWriter<Team> {
        return ItemWriter { teams ->
            teams.forEach { team ->
                teamRepository.save(team)
            }
        }
    }
}
