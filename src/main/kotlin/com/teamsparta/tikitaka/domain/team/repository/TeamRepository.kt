package com.teamsparta.tikitaka.domain.team.repository


import com.teamsparta.tikitaka.domain.team.model.Team
import org.springframework.data.jpa.repository.JpaRepository


interface TeamRepository : JpaRepository<Team, Long>, CustomTeamRepository