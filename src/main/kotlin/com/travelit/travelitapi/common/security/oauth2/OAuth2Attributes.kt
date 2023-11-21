package com.travelit.travelitapi.common.security.oauth2

class OAuth2Attribute private constructor(
    private val attributes: Map<String, Any?>,
    private val attributeKey: String,
    val email: String?,
    val name: String?,
    val picture: String?
) {
    companion object {
        fun of(provider: String, attributeKey: String, attributes: Map<String, Any?>): OAuth2Attribute {
            return when (provider) {
                "google" -> ofGoogle(attributeKey, attributes)
                "kakao" -> ofKakao("email", attributes)
                "naver" -> ofNaver("id", attributes)
                else -> throw RuntimeException()
            }
        }

        // oauth 별 내장 함수 추가
        private fun ofGoogle(attributeKey: String, attributes: Map<String, Any?>): OAuth2Attribute {
            return OAuth2Attribute(
                attributes = attributes,
                attributeKey = attributeKey,
                name = attributes["name"] as? String,
                email = attributes["email"] as? String,
                picture = attributes["picture"] as? String
            )
        }

        private fun ofKakao(attributeKey: String, attributes: Map<String, Any?>): OAuth2Attribute {
            val kakaoAccount = attributes["kakao_account"] as? Map<String, Any?>
            val kakaoProfile = kakaoAccount?.get("profile") as? Map<String, Any?>

            return OAuth2Attribute(
                attributes = kakaoAccount ?: emptyMap(),
                attributeKey = attributeKey,
                name = kakaoProfile?.get("nickname") as? String,
                email = kakaoAccount?.get("email") as? String,
                picture = kakaoProfile?.get("profile_image_url") as? String
            )
        }

        private fun ofNaver(attributeKey: String, attributes: Map<String, Any?>): OAuth2Attribute {
            val response = attributes["response"] as? Map<String, Any?>

            return OAuth2Attribute(
                attributes = response ?: emptyMap(),
                attributeKey = attributeKey,
                name = response?.get("name") as? String,
                email = response?.get("email") as? String,
                picture = response?.get("profile_image") as? String
            )
        }
    }

    fun convertToMap(): Map<String, Any?> {
        return mapOf(
            "id" to attributeKey,
            "key" to attributeKey,
            "name" to name,
            "email" to email,
            "picture" to picture
        )
    }
}