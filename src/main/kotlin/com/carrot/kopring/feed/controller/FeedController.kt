package com.carrot.kopring.account.controller

import com.amazonaws.services.s3.AmazonS3
import com.carrot.kopring.feed.service.FeedService
import com.carrot.kopring.common.logger.logger
import com.carrot.kopring.common.properties.AwsProperties
import com.carrot.kopring.feed.dto.FeedDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class FeedController(var feedService: FeedService, var awsProperties: AwsProperties, var awsS3Client: AmazonS3) {
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

    @PostMapping("/feed/image")
    fun postImage(@RequestBody(required = false) images: List<MultipartFile>) : ResponseEntity<Any> {
        try {
            val result = FeedService.uploadImage("jeongcm0101@gmail.com", images, awsProperties, awsS3Client)

            return ResponseEntity.ok().body(result)

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
