package com.teamsparta.tikitaka.domain.matchApplication.repository

import com.teamsparta.tikitaka.domain.matchApplication.model.MatchApplication
import org.springframework.data.jpa.repository.JpaRepository

interface MatchApplicationRepository : JpaRepository<MatchApplication, Long>, CustomMatchApplicationRepository