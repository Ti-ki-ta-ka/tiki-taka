package com.teamsparta.tikitaka.domain.match.controller.v1

import com.teamsparta.tikitaka.domain.common.Region
import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.dto.MatchStatusResponse
import com.teamsparta.tikitaka.domain.match.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.match.dto.UpdateMatchRequest
import com.teamsparta.tikitaka.domain.match.model.SortCriteria
import com.teamsparta.tikitaka.domain.match.service.v1.MatchService
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/matches")
class MatchController(
    private val matchService: MatchService,
) {
    @PostMapping("/create")
    fun postMatch(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: PostMatchRequest
    ): ResponseEntity<MatchStatusResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.postMatch(principal, request))
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
    fun getMatches(
        @PageableDefault(size = 20, sort = ["createdAt,desc"]) pageable: Pageable
    ): ResponseEntity<Page<MatchResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getMatches(pageable))
    }

    @GetMapping("/available")
    fun getAvailableMatchesAndSort(
        @RequestParam("sort", defaultValue = "CREATED_AT") sort: String,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<MatchResponse>> {
        val sort = SortCriteria.fromString(sort)
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getAvailableMatchesAndSort(pageable, sort))
    }

    @GetMapping("/region/{region}")
    fun getMatchesByRegionAndSort(
        @PathVariable("region") region: String,
        @RequestParam("sort", defaultValue = "CREATED_AT") sort: String,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<MatchResponse>> {
        val region = Region.fromString(region)
        val sort = SortCriteria.fromString(sort)
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getMatchesByRegionAndSort(region, pageable, sort))
    }

    @GetMapping("/{match-id}")
    fun getMatchDetails(
        @PathVariable("match-id") matchId: Long,
    ): ResponseEntity<MatchResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(matchService.getMatchDetails(matchId))
    }

    @GetMapping("/searches")
    fun searchMatch(
        pageable: Pageable,
        @RequestParam keyword: String,
    ): ResponseEntity<Page<MatchResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.searchMatch(pageable, keyword))
    }


}