package com.teamsparta.tikitaka.infra.security

import com.teamsparta.tikitaka.domain.team.model.teamMember.TeamRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val name: String,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(
        id: Long,
        name: String,
        role: TeamRole?,
    ) : this(id, name, if (role == null) emptySet<GrantedAuthority>() else setOf(SimpleGrantedAuthority("ROLE_$role")))
}