package com.teamsparta.tikitaka.domain.matching.model

import com.teamsparta.tikitaka.domain.common.baseentity.BaseEntity
import com.teamsparta.tikitaka.domain.matching.dto.UpdateMatchRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "match_post")
class Match(
    @Column(name = "match_date", nullable = false)
    var matchDate: LocalDateTime,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "match_status", nullable = false)
    var matchStatus: Boolean,

    @Column(name = "post_team_id", nullable = false)
    var teamId: Long
) : BaseEntity()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateMatch( request : UpdateMatchRequest){
        this.matchDate =request.matchDate
        this.location = request.location
        this.content = request.content
        this.matchStatus = request.matchStatus
    }


    companion object{
        fun of(matchDate: LocalDateTime, location: String, content: String, matchStatus: Boolean, teamId: Long):Match{
            return Match(
                matchDate = matchDate,
                location = location,
                content = content,
                matchStatus = matchStatus,
                teamId = teamId,
            )
        }
    }
}