package com.carrot.kopring.common.security

import com.carrot.kopring.database.entity.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserDetailsImpl(val account: Account) : UserDetails {
    var enabled: Boolean = true
    var roles: MutableSet<String>  = mutableSetOf()
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // user role 지정
        roles.add(account.role)
        return roles.stream().map { role -> SimpleGrantedAuthority("ROLE_$role") }.collect(Collectors.toSet())
    }

    override fun getPassword(): String = account.password
    override fun getUsername(): String = account.name

    override fun isAccountNonExpired(): Boolean = enabled

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean  = enabled

    override fun isEnabled(): Boolean = enabled
}

