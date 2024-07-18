package com.teamsparta.tikitaka.domain.team.repository

import com.teamsparta.tikitaka.domain.team.model.QTeam
import com.teamsparta.tikitaka.domain.team.model.Team
import com.teamsparta.tikitaka.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class QueryDslTeamRepository : QueryDslSupport() {

    private val team = QTeam.team

    fun searchTeamListByName(name: String): List<Team> {
        return queryFactory.selectFrom(team)
            .where(team.name.containsIgnoreCase(name))
            .fetch()
    }
}