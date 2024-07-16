package com.teamsparta.tikitaka.domain.match.model

import com.teamsparta.tikitaka.domain.common.baseentity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "matching_post")
class Match(
    @Column(name = "matching_date", nullable = false)
    var matchingDate: LocalDateTime,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "matching_status", nullable = false)
    var matchingStatus: Boolean,

    @Column(name = "team_id", nullable = false)
    var teamId: Long
) : BaseEntity()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}