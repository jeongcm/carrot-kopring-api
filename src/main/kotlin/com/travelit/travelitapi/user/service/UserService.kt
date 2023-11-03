package com.travelit.travelitapi.user.service

import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.user.repository.UserRepository
import com.travelit.travelitapi.database.dto.User
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
class UserService(var userRepository: UserRepository, var tokenService: TokenService) {
    @Transactional
    fun logIn(user: User): Token {
        // 1. find user and check id, password
        val foundUser = userRepository.findByName(user.userName)
                ?.takeIf { it.password == user.password } ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

        // need to make token logic
        return tokenService.createToken(foundUser)
    }
}