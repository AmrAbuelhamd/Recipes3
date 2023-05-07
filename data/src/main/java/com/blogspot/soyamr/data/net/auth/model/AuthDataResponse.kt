package com.blogspot.soyamr.data.net.auth.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AuthDataResponse(
    @SerialName("access_token")
    val token: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
)