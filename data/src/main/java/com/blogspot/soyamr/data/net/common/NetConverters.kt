package com.blogspot.soyamr.data.net.common

import com.blogspot.soyamr.data.net.auth.model.AuthDataResponse
import com.blogspot.soyamr.data.net.common.model.ApiErrorResponse
import com.blogspot.soyamr.data.net.common.model.ApiSuccessResponse
import com.blogspot.soyamr.data.net.common.model.ErrorItemResponse
import com.blogspot.soyamr.domain.auth.model.AuthData
import com.blogspot.soyamr.domain.common.extentions.orDefault
import com.blogspot.soyamr.domain.common.model.ApiError
import com.blogspot.soyamr.domain.common.model.ApiSuccess
import com.blogspot.soyamr.domain.common.model.ErrorItem

@Suppress("TooManyFunctions", "LargeClass")
object NetConverters {

    fun AuthDataResponse.toDomain() = AuthData(
        accessToken = token.orEmpty(),
        refreshToken = refreshToken.orEmpty()
    )

    fun ApiErrorResponse?.toDomain() = ApiError(
        extraMessage = this?.extraMessage.orEmpty(),
        code = this?.code.orDefault(),
        message = this?.message.orEmpty(),
        violations = this?.violations?.map {
            it.toDomain()
        }.orEmpty(),
        key = this?.key.orEmpty(),
        email = this?.mail.orEmpty()
    )

    fun ErrorItemResponse?.toDomain() = ErrorItem(
        propertyPath = this?.propertyPath.orEmpty(),
        key = this?.key.orEmpty(),
        message = this?.message.orEmpty()
    )

    fun ApiSuccessResponse.toDomain() = ApiSuccess(
        message = message.orEmpty()
    )
}