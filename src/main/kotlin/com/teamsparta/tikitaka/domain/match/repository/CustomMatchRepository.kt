package com.teamsparta.tikitaka.domain.match.repository

import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomMatchRepository {
    fun searchMatchByPageableAndKeyword(pageable: Pageable, keyword: String?): Page<MatchResponse>
}