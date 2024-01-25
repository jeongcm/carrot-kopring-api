package com.carrot.kopring.feed.service

import com.amazonaws.services.s3.model.Bucket
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.database.NotFoundEntityException
import com.carrot.kopring.database.entity.Feed
import com.carrot.kopring.feed.dto.FeedDto
import com.carrot.kopring.feed.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.multipart.MultipartFile

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val accountRepository: AccountRepository,
) {
    companion object {

        suspend fun uploadImage(userEmail: String, images: List<MultipartFile>?) = withContext(Dispatchers.IO) {
            try {
                if (images.isNullOrEmpty()) {
                    return@withContext emptyList<String>().toMutableList()
                }


                // s3 image upload
                val uploadJobs = images.map {
                    val imageMetadata = ObjectMetadata().apply {
                        this.contentType = it.contentType
                        this.contentLength = it.size
                    }

                    async {
                        val objectKey = "${userEmail}:${it.originalFilename}"
                        val putImageMetadata = PutObjectRequest(
                            "",
                            objectKey,
                            it.inputStream,
                            imageMetadata
                        )
//                    amazonS3Client.putObject(putImageMetadata)
                        objectKey
                    }
                }

                val uploadedImageKeys = uploadJobs.awaitAll()

                return@withContext uploadedImageKeys.toMutableList()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext emptyList<String>().toMutableList()
            }
        }
    }
    fun postFeed(feedDto: FeedDto) : Feed {
        val account = accountRepository.findByEmail(feedDto.email);
        if (account == null) {
            throw NotFoundEntityException(String.format("not found user %s", feedDto.email))
        }

        val feed = Feed.from(feedDto, account)
        feedRepository.save(feed);

        return feed

    }

}
