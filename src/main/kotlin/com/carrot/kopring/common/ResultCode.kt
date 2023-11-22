package com.carrot.kopring.common

enum class ResultCode(val code: Int, val description: String) {
    OK(200, "Success"),
    NO_CONTENT(204, "No Contents"),

    BAD_REQUEST(400, "Bad Request"),

    NO_AUTHORIZED(401, "No Authorized"),
    WRONG_TOKEN(401, "Wrong Token"),
    EXPIRED_TOKEN(412, "Expired Token"),
    NOT_FOUND(404, "Not Found"),
    NO_AUTHENTICATED(403, "No Authenticated"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
}