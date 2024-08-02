package com.teamsparta.tikitaka.infra.security.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
@EnableBatchProcessing
@EnableScheduling
class BatchConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val evaluationTasklet: EvaluationTasklet,
    private val simpleJobExecutionListener: SimpleJobExecutionListener,
    private val applicationContext: ApplicationContext
) {

    @Bean
    fun calculateScoreJob(): Job {
        return JobBuilder("calculateScoreJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(simpleJobExecutionListener)
            .start(calculateScoreStep())
            .build()
    }

    @Bean
    fun calculateScoreStep(): Step {
        return StepBuilder("calculateScoreStep", jobRepository)
            .tasklet(evaluationTasklet, transactionManager)
            .build()
    }

    @Scheduled(cron = "0 30 0 * * ?")
    fun perform() {
        println("Starting batch job at 00:30 AM")
        val jobLauncher = applicationContext.getBean(JobLauncher::class.java)
        val job = applicationContext.getBean("calculateScoreJob", Job::class.java)
        val jobParameters = JobParametersBuilder()
            .addDate("runDate", Date())
            .toJobParameters()
        jobLauncher.run(job, jobParameters)
    }
}
