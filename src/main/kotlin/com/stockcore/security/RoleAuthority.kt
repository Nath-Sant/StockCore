package com.stockcore.security

import com.stockcore.model.Role
import org.springframework.security.core.GrantedAuthority

class RoleAuthority(private val role: Role) : GrantedAuthority {
    override fun getAuthority(): String {
        return "ROLE_${role.name}"
    }
}
