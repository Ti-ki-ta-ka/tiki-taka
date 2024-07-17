package com.teamsparta.tikitaka.domain.match.model

import com.teamsparta.tikitaka.domain.common.baseentity.BaseEntity
import com.teamsparta.tikitaka.domain.match.dto.UpdateMatchRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "match_post")
class Match(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "match_date", nullable = false)
    var matchDate: LocalDateTime,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "match_status", nullable = false)
    var matchStatus: Boolean = false,

    @Column(name = "post_team_id", nullable = false)
    var teamId: Long
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateMatch(
        request: UpdateMatchRequest
    ) {
        validateTitle(request.title)
        validateMatchDate(request.matchDate)
        this.title = request.title
        this.matchDate = request.matchDate
        this.location = request.location
        this.content = request.content
        this.matchStatus = request.matchStatus
    }


    companion object {

        fun of(
            matchDate: LocalDateTime,
            location: String,
            content: String,
            matchStatus: Boolean,
            teamId: Long,
            title: String
        ): Match {
            return Match(
                matchDate = matchDate,
                location = location,
                content = content,
                matchStatus = matchStatus,
                teamId = teamId,
                title = title,
            ).apply {
                validateTitle(title)
                validateMatchDate(matchDate)
                validateContent(content)
            }
        }
    }

    private fun validateTitle(
        title: String
    ) {
        if (title.length > 20) {
            throw RuntimeException("제목이 너무 깁니다.") //todo : custom exception
        }
    }

    private fun validateMatchDate(
        matchDate: LocalDateTime
    ) {
        if (matchDate.isBefore(this.createdAt)) {
            throw RuntimeException("매치 날짜는 생성 날짜 이후여야 합니다") // todo: custom exception
        }
    }

    private fun validateContent(
        content: String
    ) {
        if (content.length > 1000) {
            throw RuntimeException("내용이 너무 깁니다.") //todo : custom exception
        }
    }

}