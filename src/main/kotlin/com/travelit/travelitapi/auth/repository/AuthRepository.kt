package com.travelit.travelitapi.auth.repository

import com.travelit.travelitapi.database.dto.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository : CrudRepository<User, Long> {
}