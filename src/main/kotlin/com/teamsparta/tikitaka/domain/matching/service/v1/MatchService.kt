package com.teamsparta.tikitaka.domain.matching.service.v1

import com.teamsparta.tikitaka.domain.matching.dto.MatchResponse
import com.teamsparta.tikitaka.domain.matching.dto.MatchStatusResponse
import com.teamsparta.tikitaka.domain.matching.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.matching.dto.UpdateMatchRequest

interface MatchService {

    fun postMatch(request : PostMatchRequest) : MatchStatusResponse
    fun updateMatch(matchId:Long, request : UpdateMatchRequest) : MatchStatusResponse

    fun deleteMatch(matchId:Long) : MatchStatusResponse

    fun getMatches():List<MatchResponse>

    fun getMatchDetails(matchId:Long): MatchResponse





}