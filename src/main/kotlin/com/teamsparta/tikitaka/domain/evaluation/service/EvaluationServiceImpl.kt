package com.teamsparta.tikitaka.domain.evaluation.service

import com.teamsparta.tikitaka.domain.evaluation.repository.EvaluationRepository
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class EvaluationServiceImpl(
    private val evaluationRepository: EvaluationRepository,
    private val teamRepository: TeamRepository,
) : EvaluationService {

    @Transactional
    override fun calculateAndUpdateScores() {
        val startDate = LocalDateTime.now().minusDays(90)
        val teams = teamRepository.findAll()

        teams.forEach { team ->
            val evaluations = evaluationRepository.findEvaluationsForTeamFromLast90Days(team.id!!, startDate)
            val totalMannerScore = evaluations.sumOf { it.mannerScore }
            val totalSkillScore = evaluations.sumOf { it.skillScore }
            val totalAttendanceScore = evaluations.sumOf { it.attendanceScore }
            val evaluationCount = evaluations.size

            team.mannerScore = if (evaluationCount == 0) 0 else totalMannerScore
            team.tierScore = if (evaluationCount == 0) 0 else totalSkillScore
            team.attendanceScore = if (evaluationCount == 0) 0 else totalAttendanceScore

            teamRepository.save(team)
        }
    }
}