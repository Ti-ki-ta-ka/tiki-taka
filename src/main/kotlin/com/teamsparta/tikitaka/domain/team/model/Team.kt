package com.teamsparta.tikitaka.domain.team.model

import jakarta.persistence.*

@Entity
@Table(name = "team")
class Team(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "count_member", nullable = false)
    var numMember: Int = 1,

    @Column(name = "max_member", nullable = false)
    var maxMember: Int = 50,

    @Column(name = "tier")
    var tierScore: Int = 0,

    @Column(name = "manner")
    var mannerScore: Int = 0,

    @Column(name = "attendance")
    var attendanceScore: Int = 0,

    @Column(name = "recruit_status", nullable = false)
    val recruitStatus: Boolean = false,

    @Column(name = "region", nullable = false)
    var region: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
