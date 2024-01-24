package com.carrot.kopring.feed.service

import com.carrot.kopring.feed.dto.FeedDto
import com.carrot.kopring.database.NotFoundEntityException
import com.carrot.kopring.feed.repository.FeedRepository
import com.carrot.kopring.database.InvalidRequestException
import com.carrot.kopring.database.entity.Feed
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder

@Service
@Transactional
class FeedService(
    private val feedRepository: FeedRepository,
    private val encoder: PasswordEncoder,
) {


}
