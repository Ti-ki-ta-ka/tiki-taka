package com.teamsparta.tikitaka.domain.match.dto

data class MatchStatusResponse(
    val status: Boolean,
) {
    companion object {
        fun from() = MatchStatusResponse(
            status = true,
        )
    }
}
