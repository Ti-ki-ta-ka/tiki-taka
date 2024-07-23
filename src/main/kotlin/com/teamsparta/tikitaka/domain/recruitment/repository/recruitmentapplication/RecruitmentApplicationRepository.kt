package com.teamsparta.tikitaka.domain.recruitment.repository.recruitmentapplication

import com.teamsparta.tikitaka.domain.recruitment.model.recruitmentapplication.RecruitmentApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecruitmentApplicationRepository : JpaRepository<RecruitmentApplication, Long> {
    fun findByRecruitmentId(recruitmentId: Long): List<RecruitmentApplication>?
}