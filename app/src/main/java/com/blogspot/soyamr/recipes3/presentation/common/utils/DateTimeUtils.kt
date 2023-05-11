package com.blogspot.soyamr.recipes3.presentation.common.utils

import android.text.format.DateFormat

object DateTimeUtils {
    fun formatEpochTime(epochTime: Long, dateFormatType: DateFormatType): String {
        return DateFormat.format(
            /* inFormat = */ dateFormatType.format,
            /* inTimeInMillis = */ epochTime * 1000
        ).toString()
    }

    enum class DateFormatType(val format: String) {
        FULL("dd MMMM yyyy"), SHORT("dd MMM yyyy")
    }
}