package com.travelit.travelitapi.user.service

import com.travelit.travelitapi.user.repository.UserRepository
import com.travelit.travelitapi.database.dto.User
import org.springframework.stereotype.Service

@Service
class UserService(var userRepository: UserRepository) {

    fun logIn(user: User) {
        // 1. find user
        userRepository.findAll()

        // 2. create jwt
        // 3. return jwt 토큰
    }
}