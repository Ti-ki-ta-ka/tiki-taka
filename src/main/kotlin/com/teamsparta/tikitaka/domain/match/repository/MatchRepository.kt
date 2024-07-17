package com.teamsparta.tikitaka.domain.match.repository

import com.teamsparta.tikitaka.domain.match.model.Match
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository:JpaRepository<Match,Long> {
    fun findByDeletedAtIsNull(pageable: Pageable): Page<Match>
}