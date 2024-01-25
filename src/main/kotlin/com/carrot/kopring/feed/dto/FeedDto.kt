package com.carrot.kopring.feed.dto

import com.carrot.kopring.database.entity.Account
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class FeedDto(
    @NotNull
    var region: String? = null, // KOR(Korea), GLO(Global)

    @NotNull
    var category: String? = null, // travel, food, fashion, free

    @NotBlank
    var media: List<MultipartFile>? = null, // aws s3 object key list

    @NotNull
    var title: String? = null, // feed title

    @NotNull
    var content: String? = null, // feed contents

    @Email
    @NotNull
    var email: String? = null
)