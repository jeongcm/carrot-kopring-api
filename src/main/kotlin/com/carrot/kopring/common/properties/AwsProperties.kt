package com.carrot.kopring.common.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AwsProperties {
    @Value("\${aws.s3.bucketName}")
    lateinit var bucketName: String
}