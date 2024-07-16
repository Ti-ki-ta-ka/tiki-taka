package com.teamsparta.tikitaka.domain.matching.service.v1

import com.teamsparta.tikitaka.domain.matching.dto.MatchResponse
import com.teamsparta.tikitaka.domain.matching.dto.MatchStatusResponse
import com.teamsparta.tikitaka.domain.matching.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.matching.dto.UpdateMatchRequest
import com.teamsparta.tikitaka.domain.matching.model.Match
import com.teamsparta.tikitaka.domain.matching.repository.MatchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository,

):MatchService {

    @Transactional
    override fun postMatch(request: PostMatchRequest): MatchStatusResponse {

        matchRepository.save(Match.of(
            matchDate = request.matchDate,
            location = request.location,
            content = request.content,
            matchStatus = false,
            teamId = request.teamId,
        ))
        //todo : team 구인공고 상태 변경

        return MatchStatusResponse.from()
    }

    @Transactional
    override fun updateMatch(matchId: Long, request: UpdateMatchRequest): MatchStatusResponse {
        matchRepository.findByIdOrNull(matchId)
            ?. apply { this.updateMatch(request) }
            ?: throw RuntimeException("") //todo : custom exception

        return MatchStatusResponse.from()
    }

    @Transactional
    override fun deleteMatch(matchId: Long): MatchStatusResponse {
        matchRepository.findByIdOrNull(matchId)
            ?.apply {this.softDelete()}
            ?: throw RuntimeException("Match not found") //todo : custom exception
        return MatchStatusResponse.from()
    }

    override fun getMatches(): List<MatchResponse> {
        return matchRepository.findAll()
            .map { match -> MatchResponse.from(match) }
    }

    override fun getMatchDetails(matchId: Long): MatchResponse {
        return matchRepository.findByIdOrNull(matchId)
            ?.let { match -> MatchResponse.from(match) }
            ?: throw RuntimeException("Match not found") //todo : custom exception
    }

}