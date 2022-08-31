package com.shiny.bookapp.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class BookResponse(

    @SerializedName("title")
    val title: String?,

    @SerializedName("contents")
    val contents: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("isbn")
    val isbn: String?,

    @SerializedName("datetime")
    val dateTime: Date?,

    @SerializedName("authors")
    val authors: Array<String>?,

    @SerializedName("publisher")
    val publisher: String?,

    @SerializedName("translators")
    val translators: Array<String>?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("sale_price")
    val salePrice: Int?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("status")
    val status: String?

)
