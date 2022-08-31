package com.shiny.bookapp.presentation.search

import java.util.*

// TODO 프로퍼티 줄이기
data class BookUIModel(
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