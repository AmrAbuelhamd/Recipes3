package com.blogspot.soyamr.domain.common

object Constants {
    private const val ONE_SECOND_IN_MS: Int = 1000
    const val ONE_MINUTE_IN_MS: Int = ONE_SECOND_IN_MS * 60
    const val SEARCH_DEBOUNCE = (ONE_SECOND_IN_MS * 0.8).toLong()
    const val QUERY_MIN_LENGTH = 3
}