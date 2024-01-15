package com.carrot.kopring.sample.controller

import com.carrot.kopring.sample.service.SampleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class SampleController( var sampleService: SampleService) {

    @GetMapping("/getTest")
    fun getSample(email: String): ResponseEntity<Any> {
        val result = sampleService.getSampleTest(email)
        return ResponseEntity(result, HttpStatus.OK)
    }
}