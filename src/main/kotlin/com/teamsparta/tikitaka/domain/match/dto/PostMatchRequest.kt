package com.teamsparta.tikitaka.domain.match.dto

import java.time.LocalDateTime

data class PostMatchRequest(
    val title: String,
    val matchDate: LocalDateTime,
    val location: String,
    val content: String,
    val teamId: Long,
)