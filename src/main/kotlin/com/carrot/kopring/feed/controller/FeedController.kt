package com.carrot.kopring.account.controller

import com.amazonaws.services.s3.model.ObjectMetadata
import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.feed.service.FeedService
import com.carrot.kopring.common.logger.logger
import com.carrot.kopring.common.security.response.ResponseDto
import com.carrot.kopring.database.entity.Feed
import com.carrot.kopring.feed.dto.FeedDto
import com.carrot.kopring.feed.repository.FeedRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FeedController(var feedService: FeedService, var feedRepository: FeedRepository, var accountRepository: AccountRepository) {
    var logger = logger()

    @PostMapping("/feed")
    fun postFeed(@RequestBody feedDto: FeedDto) : ResponseEntity<FeedDto> {
        try {
            feedService.postFeed(feedDto)

            return ResponseEntity.ok().body(null)

        } catch (e: Exception) {
            return ResponseEntity.status(500).build()
        }
    }
    @GetMapping("/feed")
    fun getFeedList() : ResponseEntity<List<FeedDto>> {
        return ResponseEntity.ok().body(listOf())
    }

    @GetMapping("/feed/{feedId}")
    fun getFeed(@PathVariable feedId: Int) : ResponseEntity<List<FeedDto>> {

        return ResponseEntity.ok().body(listOf())
    }
    // feed list

    // make feed
}
