package com.blogspot.soyamr.data.net.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PagingResponse<T>(
    @SerialName("total")
    val total: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("size")
    val size: Int?,
    @SerialName("items")
    val items: List<T>
)