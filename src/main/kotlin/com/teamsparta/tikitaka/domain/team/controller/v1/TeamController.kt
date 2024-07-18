package com.teamsparta.tikitaka.domain.team.controller.v1


import com.teamsparta.tikitaka.domain.team.Service.v1.TeamService
import com.teamsparta.tikitaka.domain.team.dto.request.TeamRequest
import com.teamsparta.tikitaka.domain.team.dto.response.PageResponse
import com.teamsparta.tikitaka.domain.team.dto.response.TeamResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/teams")
@RestController
class TeamController(
    private val teamService: TeamService
) {
    @GetMapping("/search")
    fun searchTeams(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "createdAt") sortBy: String,
        @RequestParam("sort_direction", defaultValue = "desc") direction: String,
        @RequestParam(value = "name") name: String,

        ): ResponseEntity<PageResponse<TeamResponse>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(teamService.searchTeamListByName(page, size, sortBy, direction, name))
    }

    @PostMapping
    fun createTeam(
        @RequestBody request: TeamRequest
    ): ResponseEntity<TeamResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(request))
    }

    @PutMapping("/{team-id}")
    fun updateTeam(
        @PathVariable("team-id") teamId: Long,
        @RequestBody request: TeamRequest
    ): ResponseEntity<TeamResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.updateTeam(request, teamId))
    }

    @DeleteMapping("/{team-id}")
    fun deleteTeam(
        @PathVariable("team-id") teamId: Long
    ): ResponseEntity<Unit> {
        teamService.deleteTeam(teamId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping
    fun getTeams(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "created_at") sortBy: String,
        @RequestParam("sort_direction", defaultValue = "desc") direction: String
    ): ResponseEntity<PageResponse<TeamResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeams(page, size, sortBy, direction))
    }


    @GetMapping("/{team-id}")
    fun getTeam(
        @PathVariable("team-id") teamId: Long
    ): ResponseEntity<TeamResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeam(teamId))
    }


}