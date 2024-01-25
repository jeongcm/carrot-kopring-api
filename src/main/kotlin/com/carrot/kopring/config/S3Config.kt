package com.carrot.kopring.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

//@Configuration
class S3Config(
    @Value("\${aws.s3.accessKey}")
    private val accessKey: String,
    @Value("\${aws.s3.secretKey}")
    private val secretKey: String,
) {
    @Bean
    fun amazonS3Client(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(
                AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))
            )
//            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }
}