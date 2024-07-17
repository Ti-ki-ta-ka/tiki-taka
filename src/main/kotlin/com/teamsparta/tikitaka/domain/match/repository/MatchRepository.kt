package com.teamsparta.tikitaka.domain.match.repository

import com.teamsparta.tikitaka.domain.match.model.Match
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository : JpaRepository<Match, Long>, CustomMatchRepository {
    fun findByDeletedAtIsNull(): List<Match>
}