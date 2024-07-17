package com.teamsparta.tikitaka.domain.match.service.v1

import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.dto.MatchStatusResponse
import com.teamsparta.tikitaka.domain.match.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.match.dto.UpdateMatchRequest
import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository,

):MatchService {

    @Transactional
    override fun postMatch(
        request: PostMatchRequest
    ): MatchStatusResponse
    {

        matchRepository.save(Match.of(
            title = request.title,
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
    override fun updateMatch(
        matchId: Long, request: UpdateMatchRequest
    ): MatchStatusResponse
    {
        matchRepository.findByIdOrNull(matchId)
            ?. let { it.updateMatch(request) }
            ?: throw RuntimeException("") //todo : custom exception

        return MatchStatusResponse.from()
    }

    @Transactional
    override fun deleteMatch(
        matchId: Long
    ): MatchStatusResponse
    {
        matchRepository.findByIdOrNull(matchId)
            ?.let {it.softDelete()}
            ?: throw RuntimeException("Match not found") //todo : custom exception
        return MatchStatusResponse.from()
    }

    override fun getMatches(pageable: Pageable): Page<MatchResponse> {
        return matchRepository.findByDeletedAtIsNull(pageable)
            .map { match -> MatchResponse.from(match) }
    }

    override fun getMatchDetails(
        matchId: Long
    ): MatchResponse
    {
        return matchRepository.findByIdOrNull(matchId)
            ?.let { match -> MatchResponse.from(match) }
            ?: throw RuntimeException("Match not found") //todo : custom exception
    }

}