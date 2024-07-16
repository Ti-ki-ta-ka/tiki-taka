package com.teamsparta.tikitaka.domain.matchApplication.model

import com.teamsparta.tikitaka.domain.match.model.Match
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "match_application")
class MatchApplication(
    @ManyToOne
    @JoinColumn(name = "match_post_id", nullable = false)
    val matchPost: Match,

    @Column(name = "apply_team_id", nullable = false)
    var applyTeamId: Long,

    @Enumerated(EnumType.STRING)
    var approveStatus: ApproveStatus = ApproveStatus.WAITING,

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

    companion object {
        fun of(matchPost: Match, applyTeamId: Long): MatchApplication {
            val timestamp = LocalDateTime.now()

            return MatchApplication(
                matchPost,
                applyTeamId = applyTeamId,
                createdAt = timestamp
            )
        }
    }
}