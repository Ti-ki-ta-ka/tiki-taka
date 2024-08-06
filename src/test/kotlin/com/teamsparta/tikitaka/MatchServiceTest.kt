package com.teamsparta.tikitaka

import com.teamsparta.tikitaka.domain.common.Region
import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import com.teamsparta.tikitaka.domain.match.service.v1.MatchServiceImpl
import com.teamsparta.tikitaka.domain.team.repository.TeamRepository
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MatchServiceTest {
    val matchRepository: MatchRepository = mock(MatchRepository::class.java)
    val teamRepository: TeamRepository = mock(TeamRepository::class.java)
    val teamMemberRepository: TeamMemberRepository = mock(TeamMemberRepository::class.java)
    val matchService = MatchServiceImpl(matchRepository, teamRepository, teamMemberRepository)

    @Test
    fun `매치 조회시 MatchResponse의 페이지 반환 `() {
        val pageable: Pageable = mock(Pageable::class.java)
        val match = Match(
            teamId = 1L,
            userId = 1L,
            title = "제목",
            matchDate = LocalDateTime.now().plusDays(1),
            region = Region.BUSAN,
            location = "어딘가",
            content = "내용",
            matchStatus = true,
        ).apply {
            id = 1L
        }
        val matchResponse = MatchResponse.from(match)
        val matches = listOf(match)
        val page: Page<Match> = PageImpl(matches, pageable, matches.size.toLong())

        Mockito.`when`(matchRepository.findAll(pageable)).thenReturn(page)

        val result = matchService.getMatches(pageable)
        assertEquals(1, result.numberOfElements)
        assertEquals(matchResponse, result.content[0])
    }

    @Test
    fun `매치가 없을 경우 빈 페이지 반환`() {
        val pageable: Pageable = mock(Pageable::class.java)
        val emptyPage: Page<Match> = PageImpl(emptyList(), pageable, 0)

        Mockito.`when`(matchRepository.findAll(pageable)).thenReturn(emptyPage)

        val result = matchService.getMatches(pageable)
        assertEquals(0, result.numberOfElements)
        assertTrue(result.content.isEmpty())
    }

    @Test
    fun `정렬된 매치 목록 반환`() {
        val pageable: Pageable = PageRequest.of(0, 10, Sort.by("matchDate").ascending())
        val match1 = Match(
            teamId = 1L,
            userId = 1L,
            title = "제목1",
            matchDate = LocalDateTime.now().plusDays(1),
            region = Region.BUSAN,
            location = "어딘가",
            content = "내용",
            matchStatus = true,
        ).apply {
            id = 1L; title = "매치1"
        }
        val match2 = Match(
            teamId = 1L,
            userId = 1L,
            title = "제목1",
            matchDate = LocalDateTime.now().plusDays(1),
            region = Region.BUSAN,
            location = "어딘가",
            content = "내용",
            matchStatus = true,
        ).apply {
            id = 1L; title = "매치2"
        }
        val matches = listOf(match1, match2)
        val page: Page<Match> = PageImpl(matches, pageable, matches.size.toLong())

        Mockito.`when`(matchRepository.findAll(pageable)).thenReturn(page)

        val result = matchService.getMatches(pageable)
        assertEquals(2, result.numberOfElements)
        assertEquals("매치1", result.content[0].title)
        assertEquals("매치2", result.content[1].title)
    }
}