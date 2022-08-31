package com.shiny.bookapp.data.models

import com.google.gson.annotations.SerializedName

data class SearchResponse<T>(
    val meta: MetaResponse?,
    val documents: List<T>?
)

data class MetaResponse(

    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("pageable_count")
    val pageableCount: Int?,

    @SerializedName("is_end")
    val isEnd: Boolean?

)