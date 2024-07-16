package com.teamsparta.tikitaka.domain.matching.dto

import java.time.LocalDateTime

class UpdateMatchRequest (
    val matchDate : LocalDateTime,
    val location : String,
    val content : String,
    val matchStatus : Boolean,
)