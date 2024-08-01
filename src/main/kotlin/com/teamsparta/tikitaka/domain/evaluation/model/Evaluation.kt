package com.teamsparta.tikitaka.domain.evaluation.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "evaluation")
@SQLRestriction("deleted_at is null")
class Evaluation(
    @Column(name = "evaluator_team_id", nullable = false)
    val evaluatorTeamId: Long,

    @Column(name = "evaluatee_team_id", nullable = false)
    val evaluateeTeamId: Long,

    @Column(name = "evaluator_id", nullable = false)
    val evaluatorId: Long,

    @Column(name = "manner_score", nullable = false)
    var mannerScore: Int = 0,

    @Column(name = "skill_score", nullable = false)
    var skillScore: Int = 0,

    @Column(name = "attendance_score", nullable = false)
    var attendanceScore: Int = 0,

    @Column(name = "evaluation_status", nullable = false)
    var evaluationStatus: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "deleted_at", nullable = true)
    val deletedAt: LocalDateTime

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}