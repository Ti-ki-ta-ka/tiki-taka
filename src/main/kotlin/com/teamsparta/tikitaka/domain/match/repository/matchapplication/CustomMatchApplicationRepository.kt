package com.teamsparta.tikitaka.domain.match.repository.matchapplication

import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication
import java.time.LocalDate

interface CustomMatchApplicationRepository {
    fun findByTeamIdAndMatchDate(teamId: Long, matchDate: LocalDate): List<MatchApplication>
    fun findByApplyTeamId(applyTeamId: Long): List<MatchApplication>
}