package com.teamsparta.tikitaka.domain.matching.dto

import java.time.LocalDateTime

data class PostMatchRequest (
    val title :String,
    val matchDate : LocalDateTime,
    val location : String, //enum ?
    val content : String,
    val teamId : Long,
)