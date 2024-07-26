package com.teamsparta.tikitaka.domain.recruitment.repository.recruitmentapplication

import com.teamsparta.tikitaka.domain.recruitment.model.recruitmentapplication.RecruitmentApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecruitmentApplicationRepository : JpaRepository<RecruitmentApplication, Long>,
    CustomRecruitmentApplicationRepository {
    fun findByRecruitmentId(recruitmentId: Long): List<RecruitmentApplication>?
    fun findByIdAndRecruitmentId(ApplicationId: Long, RecruitmentId: Long): RecruitmentApplication?
    fun findByUserId(UserId: Long): List<RecruitmentApplication>?
}