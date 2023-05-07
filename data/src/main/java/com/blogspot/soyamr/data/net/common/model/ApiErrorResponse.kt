package com.blogspot.soyamr.data.net.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val extraMessage: String? = null,
    val code: Int? = null,
    val message: String? = null,
    val violations: List<ErrorItemResponse>? = null,
    val key: String? = null,
    val mail: String? = null
)

@Serializable
data class ErrorItemResponse(
    val propertyPath: String? = null,
    val key: String? = null,
    val message: String? = null
)