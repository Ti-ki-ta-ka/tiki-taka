package com.teamsparta.tikitaka.domain.match.dto

import com.teamsparta.tikitaka.domain.common.Region
import java.time.LocalDateTime

class UpdateMatchRequest(
    val title: String,
    val matchDate: LocalDateTime,
    val region: Region,
    val location: String,
    val content: String,
    val matchStatus: Boolean,
)