package com.teamsparta.tikitaka.domain.match.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.Expressions
import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.model.QMatch
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MatchRepositoryImpl : CustomMatchRepository, QueryDslSupport() {
    private val match = QMatch.match

    override fun searchMatchByPageableAndKeyword(
        pageable: Pageable,
        keyword: String?
    ): Page<MatchResponse> {
        val whereClause = BooleanBuilder()

        keyword?.let {
            whereClause.and(
                match.title.containsIgnoreCase(it)
                    .or(match.content.containsIgnoreCase(it))
                    .or(match.location.containsIgnoreCase(it))
            )
        }

        val totalCount = queryFactory.select(match.count())
            .from(match)
            .where(whereClause)
            .fetchOne() ?: 0L


        val matches = queryFactory.selectFrom(match)
            .where(whereClause)
            .orderBy(match.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val matchResponse = matches.map { match ->
            MatchResponse(
                id = match.id!!,
                teamId = match.teamId,
                title = match.title,
                matchDate = match.matchDate,
                location = match.location,
                content = match.content,
                matchStatus = match.matchStatus,
                createdAt = match.createdAt,
            )
        }
        return PageImpl(matchResponse, pageable, totalCount)
    }

    override fun getMatchesByDeadline(pageable: Pageable): Page<MatchResponse> {

        val totalCount = queryFactory.select(match.count())
            .from(match)
            .where(match.matchStatus.eq(false))
            .fetchOne() ?: 0L

        val matches = queryFactory.selectFrom(match)
            .where(match.matchStatus.eq(false))
            .orderBy(
                Expressions.dateTemplate(
                    LocalDateTime::class.java,
                    "dateadd(day, -1, {0})",
                    match.matchDate
                ).asc()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val matchResponse = matches.map { match ->
            MatchResponse(
                id = match.id!!,
                teamId = match.teamId,
                title = match.title,
                matchDate = match.matchDate,
                location = match.location,
                content = match.content,
                matchStatus = match.matchStatus,
                createdAt = match.createdAt,
            )
        }

        return PageImpl(matchResponse, pageable, totalCount)
    }

    override fun getMatchesAvailable(pageable: Pageable): Page<MatchResponse> {

        val totalCount = queryFactory.select(match.count())
            .from(match)
            .where(match.matchStatus.eq(false))
            .fetchOne() ?: 0L

        val matches = queryFactory.selectFrom(match)
            .where(match.matchStatus.eq(false))
            .orderBy(match.createdAt.asc()) // Sorting by createdAt
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val matchResponse = matches.map { match ->
            MatchResponse(
                id = match.id!!,
                teamId = match.teamId,
                title = match.title,
                matchDate = match.matchDate,
                location = match.location,
                content = match.content,
                matchStatus = match.matchStatus,
                createdAt = match.createdAt,
            )
        }

        return PageImpl(matchResponse, pageable, totalCount)
    }
}
