package com.teamsparta.tikitaka.domain.team.repository

import com.teamsparta.tikitaka.domain.team.model.Team
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomTeamRepository {
    fun findAllByPageable(pageable: Pageable): Page<Team>

    fun findByName(pageable: Pageable, name: String): Page<Team>

}