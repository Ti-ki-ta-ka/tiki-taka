package com.teamsparta.tikitaka.infra.security.config

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Component

@Component
class SimpleJobExecutionListener : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("Job completed!")
    }
}