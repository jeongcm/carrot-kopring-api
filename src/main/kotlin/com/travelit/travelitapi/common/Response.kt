package com.travelit.travelitapi.common

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseDto<T>(
        val timestamp: Date = Date(),
        val code: Int,
        val desc: String,
        val data: T? = null
) {
    constructor(resultCode: ResultCode) : this(
            code = resultCode.code,
            desc = resultCode.description
    )

    constructor(resultCode: ResultCode, data: T) : this(
            code = resultCode.code,
            desc = resultCode.description,
            data = data
    )
}
