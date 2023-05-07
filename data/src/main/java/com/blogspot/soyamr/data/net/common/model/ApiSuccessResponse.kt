package com.blogspot.soyamr.data.net.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiSuccessResponse(
    val message: String? = null
)