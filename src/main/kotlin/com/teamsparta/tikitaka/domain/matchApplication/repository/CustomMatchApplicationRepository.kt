package com.teamsparta.tikitaka.domain.matchApplication.repository

import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication
import java.time.LocalDate

interface CustomMatchApplicationRepository {
    fun findByTeamIdAndMatchDate(teamId: Long, matchDate: LocalDate): List<MatchApplication>
    fun findByApplyTeamId(applyTeamId: Long): List<MatchApplication>
}