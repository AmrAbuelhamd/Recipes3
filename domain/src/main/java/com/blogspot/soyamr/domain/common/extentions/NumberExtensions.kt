package com.blogspot.soyamr.domain.common.extentions

import java.math.RoundingMode

val Double?.isWholeNumber: Boolean get() = this?.rem(1) == 0.0

fun Double.round(places: Int): Double =
    this.toBigDecimal().setScale(places, RoundingMode.FLOOR).toDouble()

fun Double.removeTrailingZeros(): String = String.format("%.0f", this)

fun Double.roundRemovingTrailingZeros(places: Int = 1): String {
    val roundedNumber = this.round(places)
    return if (roundedNumber % 1 == 0.0) {
        roundedNumber.removeTrailingZeros()
    } else {
        roundedNumber
    }.toString()
}

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true

fun Long?.orZero() = this ?: 0

fun Int?.orZero() = this ?: 0

fun Float?.orZero() = this ?: 0.0f

fun Double?.orZero() = this ?: 0.0

fun Long?.orDefault(): Long = this ?: -1

fun Int?.orDefault(): Int = this ?: -1

fun Float?.orDefault(): Float = this ?: -1.0f

fun Double?.orDefault(): Double = this ?: -1.0

fun Long.isDefault(): Boolean = this == -1L

fun Int.isDefault(): Boolean = this == -1

fun Float.isDefault(): Boolean = this == -1.0f

fun Double.isDefault(): Boolean = this == -1.0