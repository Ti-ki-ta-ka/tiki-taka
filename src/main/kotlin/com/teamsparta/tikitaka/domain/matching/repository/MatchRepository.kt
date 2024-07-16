package com.teamsparta.tikitaka.domain.matching.repository

import com.teamsparta.tikitaka.domain.matching.model.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository:JpaRepository<Match,Long> {
    fun findByDeletedAtIsNull(): List<Match>
}