package com.carrot.kopring.feed.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.common.properties.AwsProperties
import com.carrot.kopring.database.NotFoundEntityException
import com.carrot.kopring.database.entity.Feed
import com.carrot.kopring.feed.dto.FeedDto
import com.carrot.kopring.feed.repository.FeedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val accountRepository: AccountRepository,
    @Autowired private val awsProperties: AwsProperties,
    @Autowired private val awsS3Client: AmazonS3,
) {
    companion object  {
        fun uploadImage(userEmail: String, images: List<MultipartFile>?, awsProperties: AwsProperties, awsS3Client: AmazonS3) :MutableList<String> {
            try {

                if (images.isNullOrEmpty()) {
                    return emptyList<String>().toMutableList()
                }

                // s3 image upload
                val uploadJobs = images.map {image ->

                    val imageMetadata = ObjectMetadata().apply {
                        this.contentType = image.contentType
                        this.contentLength = image.size
                    }
                    val objectKey = "${userEmail}:${image.originalFilename}"
                    image.inputStream.use { inputStream ->
                        val putImageMetadata = PutObjectRequest(
                            awsProperties.bucketName,
                            objectKey,
                            inputStream,
                            imageMetadata
                        )

                        awsS3Client.putObject(putImageMetadata)

                    }
                    objectKey
                }

                return uploadJobs.toMutableList()
            } catch (e: Exception) {
                e.printStackTrace()
                return emptyList<String>().toMutableList()
            }
        }
    }
    fun postFeed(feedDto: FeedDto) : Feed {
        val account = accountRepository.findByEmail(feedDto.email);
        if (account == null) {
            throw NotFoundEntityException(String.format("not found user %s", feedDto.email))
        }

        val feed = Feed.from(feedDto, account, awsProperties, awsS3Client)
        feedRepository.save(feed);

        return feed

    }

}
