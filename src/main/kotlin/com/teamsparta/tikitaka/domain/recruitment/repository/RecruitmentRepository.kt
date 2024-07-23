package com.teamsparta.tikitaka.domain.recruitment.repository

import com.teamsparta.tikitaka.domain.recruitment.model.Recruitment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecruitmentRepository : JpaRepository<Recruitment, Long>