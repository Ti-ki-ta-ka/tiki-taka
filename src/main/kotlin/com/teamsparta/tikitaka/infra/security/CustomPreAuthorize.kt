package com.teamsparta.tikitaka.infra.security

import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.team.model.teamMember.TeamRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class CustomPreAuthorize {
    fun <T> hasAnyRole(principal: UserPrincipal, validRoles: Set<TeamRole>, action: () -> T): T {
        val validAuthorities = validRoles.map { SimpleGrantedAuthority("ROLE_$it") }.toSet()
        if (principal.authorities.none { it in validAuthorities }) {
            throw AccessDeniedException("Not allowed to this API")
        }
        return action()
    }
}
