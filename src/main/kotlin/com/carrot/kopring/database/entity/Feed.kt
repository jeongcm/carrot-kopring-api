package com.carrot.kopring.database.entity

import com.carrot.kopring.feed.dto.FeedDto
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity
class Feed (
    @Column(nullable = false)
    var region: String = "KOR", // KOR(Korea), GLO(Global)

    @Column(nullable = false)
    var category: String = "travel", // travel, food, fashion, free

    @Column(nullable = true)
    @ElementCollection
    var media: MutableList<String>? = null, // aws s3 object key list

    @Column(nullable = false)
    var title: String = "", // feed title

    @Column(nullable = false)
    var content: String = "", // feed contents

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email", referencedColumnName = "email")
    var account: Account? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
): AuditableEntity() {
    companion object {
        fun from(feed: FeedDto) = Account(

        )
    }

    fun update(feed: FeedDto) {	// 파라미터에 PasswordEncoder 추가

    }

    fun delete() {
        this.deletedAt = LocalDateTime.now(ZoneOffset.UTC)
    }
}