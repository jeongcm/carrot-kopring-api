package com.travelit.travelitapi.common.security

import com.travelit.travelitapi.account.repository.AccountRepository
import com.travelit.travelitapi.database.NotFoundEntityException
import com.travelit.travelitapi.database.dto.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

class UserDetailsImpl(val account: Account) : UserDetails {
    var enabled: Boolean = true
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = AuthorityUtils.createAuthorityList()

    override fun getPassword(): String = account.password
    override fun getUsername(): String = account.name

    override fun isAccountNonExpired(): Boolean = enabled

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean  = enabled

    override fun isEnabled(): Boolean = enabled
}

