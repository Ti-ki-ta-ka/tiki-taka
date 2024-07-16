package com.teamsparta.tikitaka.domain.matching.dto

import com.teamsparta.tikitaka.domain.matching.model.Match

data class MatchStatusResponse(
    val status : Boolean,
){
    companion object{
        fun from() = MatchStatusResponse(
            status = true,
        )
    }
}
