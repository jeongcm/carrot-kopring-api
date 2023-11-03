package com.travelit.travelitapi.user.service

import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.user.repository.UserRepository
import com.travelit.travelitapi.database.dto.User
import org.springframework.stereotype.Service

@Service
class UserService(var userRepository: UserRepository) {
    fun findUser(user: User): User? {
        var foundUser: User? = userRepository.findByName(user.userName)

        return foundUser
    }

    fun logIn(user: User): Token?{
        var token = Token("", "")
        // 2. create jwt

        // 3. return jwt 토큰


        return token
    }
}