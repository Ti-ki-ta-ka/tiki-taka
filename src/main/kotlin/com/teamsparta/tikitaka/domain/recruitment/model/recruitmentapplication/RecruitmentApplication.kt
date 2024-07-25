package com.teamsparta.tikitaka.domain.recruitment.model.recruitmentapplication

import com.teamsparta.tikitaka.domain.recruitment.model.Recruitment
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "recruitment_application")
@SQLRestriction("deleted_at is null")
class RecruitmentApplication(
    @ManyToOne
    @JoinColumn(name = "recruitment_id", nullable = false)
    val recruitment: Recruitment,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "team_id", nullable = false)
    val teamId: Long,

    @Enumerated(EnumType.STRING)
    var responseStatus: ResponseStatus = ResponseStatus.WAITING,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun delete() {
        this.deletedAt = LocalDateTime.now()
    }
}