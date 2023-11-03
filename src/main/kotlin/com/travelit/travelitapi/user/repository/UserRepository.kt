package com.travelit.travelitapi.user.repository

import com.travelit.travelitapi.database.dto.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
}