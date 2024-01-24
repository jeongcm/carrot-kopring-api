package com.carrot.kopring.account.controller

import com.carrot.kopring.feed.service.FeedService
import com.carrot.kopring.common.logger.logger
import com.carrot.kopring.common.security.response.ResponseDto
import com.carrot.kopring.database.entity.Feed
import com.carrot.kopring.feed.dto.FeedDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FeedController(var feedService: FeedService) {
    var logger = logger()

    @PostMapping("/feed")
    fun postFeed() : ResponseEntity<FeedDto> {
        return ResponseEntity.ok().body(null)
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
