package com.teamsparta.tikitaka.domain.match.repository.matchapplication

import com.teamsparta.tikitaka.domain.match.model.matchapplication.MatchApplication
import org.springframework.data.jpa.repository.JpaRepository

interface MatchApplicationRepository : JpaRepository<MatchApplication, Long>, CustomMatchApplicationRepository {
    fun findByApplyTeamIdAndMatchPostId(applyTeamId: Long, matchPostId: Long): List<MatchApplication>
}