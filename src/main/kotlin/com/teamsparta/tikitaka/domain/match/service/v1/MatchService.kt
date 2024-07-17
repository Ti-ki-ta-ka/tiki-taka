package com.teamsparta.tikitaka.domain.match.service.v1

import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.dto.MatchStatusResponse
import com.teamsparta.tikitaka.domain.match.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.match.dto.UpdateMatchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MatchService {

    fun postMatch(request: PostMatchRequest): MatchStatusResponse
    fun updateMatch(matchId: Long, request: UpdateMatchRequest): MatchStatusResponse
    fun deleteMatch(matchId: Long): MatchStatusResponse
    fun getMatches(pageable: Pageable): Page<MatchResponse>
    fun getMatchDetails(matchId: Long): MatchResponse


}