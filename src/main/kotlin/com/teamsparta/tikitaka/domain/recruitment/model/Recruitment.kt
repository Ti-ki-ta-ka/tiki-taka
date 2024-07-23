package com.teamsparta.tikitaka.domain.recruitment.model

import com.teamsparta.tikitaka.domain.common.baseentity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "recruitment")
@SQLRestriction("deleted_at is null")
class Recruitment(

    @Column(name = "team_id", nullable = false)
    val teamId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val recruitType: RecruitType,

    @Column(name = "quantity", nullable = false)
    var quantity: Int,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "closing_status", nullable = false)
    var closingStatus: Boolean = false,

    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    //todo : updateMatch

    companion object {
        fun of(
            teamId: Long,
            userId: Long,
            recruitType: RecruitType,
            quantity: Int,
            content: String,
            closingStatus: Boolean,
        ): Recruitment {
            return Recruitment(
                teamId = teamId,
                userId = userId,
                recruitType = recruitType,
                quantity = quantity,
                content = content,
                closingStatus = closingStatus,
            )
        }
    }


}
