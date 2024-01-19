package com.carrot.kopring.sample.controller

import com.carrot.kopring.sample.dto.FileResponseTest
import com.carrot.kopring.sample.service.SampleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.models.media.MediaType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/test")
@Tag(name = "Test", description = "Test API")
class SampleController( var sampleService: SampleService) {

    @GetMapping("/getTest")
    fun getSample(role: String): ResponseEntity<Any> {
        val result = sampleService.getSampleTest(role)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @Operation(summary = "Get filename", description = "파일 객체에서 파일 이름을 가져온다.")
    @PostMapping(path =["/file"], consumes = ["multipart/form-data"] , produces = ["application/json"])
    fun upload(
            @Parameter(
                    description = "multipart/form-data 형식의 이미지를 input으로 받습니다. 이때 key 값은 file 입니다.",
            )
            @RequestPart("file") file: MultipartFile): ResponseEntity<FileResponseTest> {

        return ResponseEntity.ok().body(file.originalFilename?.let { FileResponseTest(filename = it) })
    }
}
