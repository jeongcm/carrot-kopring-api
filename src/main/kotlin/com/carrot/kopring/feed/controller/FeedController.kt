package com.carrot.kopring.account.controller

import com.carrot.kopring.feed.service.FeedService
import com.carrot.kopring.common.logger.logger
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feed")
class FeedController(var feedService: FeedService) {
    var logger = logger()

    // feed list

    // make feed
}
