package com.teamsparta.tikitaka.domain.matchApplication.model

import com.teamsparta.tikitaka.domain.match.model.Match
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "match_application")
class MatchApplication(
    @ManyToOne
    @JoinColumn(name = "match_post_id", nullable = false)
    val postMatch: Match,

    @Column(name = "apply_team_id", nullable = false)
    var applyTeamId: Long,

    @Enumerated(EnumType.STRING)
    var approveStatus: ApproveStatus,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime = LocalDateTime.now(),
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}