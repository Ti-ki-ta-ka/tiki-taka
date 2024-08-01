package com.teamsparta.tikitaka.domain.evaluation.repository

import com.teamsparta.tikitaka.domain.evaluation.model.Evaluation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EvaluationRepository : JpaRepository<Evaluation, Long>, CustomEvaluationRepository