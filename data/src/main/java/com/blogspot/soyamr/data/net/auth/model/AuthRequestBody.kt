package com.blogspot.soyamr.data.net.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestBody(
    val phone: String,
)