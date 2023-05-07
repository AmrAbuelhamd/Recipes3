package com.blogspot.soyamr.domain.common.model

import java.io.IOException

// it's critical to throw IOException inside Retrofit's pipeline or you'll get RuntimeException

class NoInternetFailure(message: String? = null) : IOException(message)

class CommonBackendFailure(val apiError: ApiError? = null) : IOException(apiError?.message)

class UnauthorizedFailure(val apiError: ApiError? = null) : IOException(apiError?.message)

class UnknownFailure(message: String? = null) : IOException(message)