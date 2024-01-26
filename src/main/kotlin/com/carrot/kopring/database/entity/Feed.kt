package com.carrot.kopring.database.entity

import com.amazonaws.services.s3.AmazonS3
import com.carrot.kopring.common.properties.AwsProperties
import com.carrot.kopring.feed.dto.FeedDto
import com.carrot.kopring.feed.service.FeedService
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity
class Feed(
    @Column(nullable = false)
    var region: String = "KOR", // KOR(Korea), GLO(Global)

    @Column(nullable = false)
    var category: String = "travel", // travel, food, fashion, free

    @Column(nullable = true)
    @ElementCollection
    var media: MutableList<String>? = null, // aws s3 object key list

    @Column(nullable = false)
    var title: String = "제목없음", // feed title

    @Column(nullable = false)
    var content: String? = "", // feed contents

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email", referencedColumnName = "email")
    var account: Account? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditableEntity() {
    companion object {


        fun from(feedDto: FeedDto, account: Account, awsProperties: AwsProperties, awsS3Client: AmazonS3): Feed {

            val feed = Feed(
                region = feedDto.region ?: "KOR",
                category = feedDto.category ?: "travel",
                title = feedDto.title ?: "제목없음",
                content = feedDto.content ?: "",
                account = account
            )

            feed.media = FeedService.uploadImage(account.email, feedDto.media, awsProperties, awsS3Client)

            return feed
        }
    }

    fun update(feed: FeedDto) {    // 파라미터에 PasswordEncoder 추가

    }

    fun delete() {
        this.deletedAt = LocalDateTime.now(ZoneOffset.UTC)
    }
}