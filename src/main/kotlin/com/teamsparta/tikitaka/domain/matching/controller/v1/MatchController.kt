package com.teamsparta.tikitaka.domain.matching.controller.v1

import com.teamsparta.tikitaka.domain.matching.dto.MatchResponse
import com.teamsparta.tikitaka.domain.matching.dto.MatchStatusResponse
import com.teamsparta.tikitaka.domain.matching.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.matching.dto.UpdateMatchRequest
import com.teamsparta.tikitaka.domain.matching.service.v1.MatchService
import com.teamsparta.tikitaka.domain.matching.service.v1.MatchServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/matches")
class MatchController(
    private val matchService: MatchService,
) {
    //@PreAuthorize("hasRole('LEADER')") //todo : 리더 외 권한 부여 ?
    @PostMapping()
    fun postMatch(@RequestBody request: PostMatchRequest): ResponseEntity<MatchStatusResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.postMatch(request))
    }

    //@PreAuthorize("hasRole('LEADER')") //todo : 리더 외 작성자
    @PutMapping("/{match-id}")
    fun updateMatch(
        @PathVariable(name = "match-id") matchId: Long,
        @RequestBody request: UpdateMatchRequest,
    ): ResponseEntity<MatchStatusResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.updateMatch(matchId, request))
    }

    //@PreAuthorize("hasRole('LEADER')")
    @DeleteMapping("/{match-id}")
    fun deleteMatch(@PathVariable(name = "match-id") matchId: Long): ResponseEntity<MatchStatusResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(matchService.deleteMatch(matchId))
    }

    @GetMapping()
    fun getMatches(): ResponseEntity<List<MatchResponse>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(matchService.getMatches())
    }

    @GetMapping("/{match-id}")
    fun getMatchDetails(
        @PathVariable("match-id") matchId: Long,
    ): ResponseEntity<MatchResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(matchService.getMatchDetails(matchId))
    }

}