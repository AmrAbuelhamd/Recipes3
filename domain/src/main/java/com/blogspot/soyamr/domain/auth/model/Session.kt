package com.blogspot.soyamr.domain.auth.model

data class Session(
    val type: SessionType,
    val phone: String,
    val accessToken: String,
    val refreshToken: String,
) {
    fun isAuthorized(): Boolean = type == SessionType.AUTHORIZED_USER

    companion object {
        val EMPTY = Session(
            type = SessionType.NONE,
            accessToken = "",
            refreshToken = "",
            phone = "",
        )
    }
}
