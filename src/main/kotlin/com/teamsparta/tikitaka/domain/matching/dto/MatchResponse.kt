package com.teamsparta.tikitaka.domain.matching.dto

import com.teamsparta.tikitaka.domain.matching.model.Match
import java.time.LocalDateTime

data class MatchResponse(

    val id :Long,
    val team_id : Long,
    val matchDate :LocalDateTime,
    val location : String,
    val content : String,
    val matchStatus : Boolean,

){
    companion object{
        fun from(match : Match) = MatchResponse(
            id = match.id!!,
            team_id = match.teamId,
            matchDate = match.matchDate,
            location = match.location,
            content = match.content,
            matchStatus = match.matchStatus,

        )
    }
}
