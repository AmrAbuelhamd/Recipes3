package com.blogspot.soyamr.domain.common.model

data class ApiError(
    val extraMessage: String,
    val code: Int,
    val message: String,
    val violations: List<ErrorItem>,
    val key: String,
    val email: String,
)

data class ErrorItem(
    val propertyPath: String,
    val key: String,
    val message: String
)