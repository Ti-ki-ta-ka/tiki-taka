package com.teamsparta.tikitaka.domain.match.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.Expressions
import com.teamsparta.tikitaka.domain.common.Region
import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.model.QMatch
import com.teamsparta.tikitaka.domain.match.model.SortCriteria
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
                region = match.region,
            )
        }
        return PageImpl(matchResponse, pageable, totalCount)
    }

    override fun getAvailableMatchesAndSort(pageable: Pageable, sortCriteria: SortCriteria): Page<MatchResponse> {
        val totalCount = queryFactory.select(match.count())
            .from(match)
            .where(match.matchStatus.eq(false))
            .fetchOne() ?: 0L

        val query = queryFactory.selectFrom(match)
            .where(match.matchStatus.eq(false))

        when (sortCriteria) {
            SortCriteria.CREATED_AT -> query.orderBy(match.createdAt.asc())
            SortCriteria.DEADLINE -> query.orderBy(
                Expressions.dateTemplate(
                    LocalDateTime::class.java,
                    "dateadd(day, -1, {0})",
                    match.matchDate
                ).asc()
            )
        }

        val matches = query
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
                region = match.region
            )
        }

        return PageImpl(matchResponse, pageable, totalCount)
    }

    override fun getMatchesByRegionAndSort(
        region: Region,
        pageable: Pageable,
        sortCriteria: SortCriteria
    ): Page<MatchResponse> {
        val whereClause = BooleanBuilder().apply {
            and(match.region.eq(region))
            if (sortCriteria == SortCriteria.DEADLINE) {
                and(match.matchStatus.eq(false))
            }
        }

        val totalCount = queryFactory.select(match.count())
            .from(match)
            .where(whereClause)
            .fetchOne() ?: 0L

        val query = queryFactory.selectFrom(match)
            .where(whereClause)

        when (sortCriteria) {
            SortCriteria.CREATED_AT -> query.orderBy(match.createdAt.desc())
            SortCriteria.DEADLINE -> query.orderBy(
                Expressions.dateTemplate(
                    LocalDateTime::class.java,
                    "dateadd(day, -1, {0})",
                    match.matchDate
                ).asc()
            )
        }

        val matches = query
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
                region = match.region,
                createdAt = match.createdAt,
            )
        }

        return PageImpl(matchResponse, pageable, totalCount)
    }
}
