package com.shiny.bookapp.domain.entities

import java.util.*

data class SearchEntity(
    val hasNext: Boolean,
    val searchedList: List<BookEntity>,
)

data class BookEntity(
    val title: String?,
    val contents: String?,
    val url: String?,
    val isbn: String?,
    val dateTime: Date?,
    val authors: Array<String>?,
    val publisher: String?,
    val translators: Array<String>?,
    val price: Int?,
    val salePrice: Int?,
    val thumbnail: String?,
    val status: String?,
)
