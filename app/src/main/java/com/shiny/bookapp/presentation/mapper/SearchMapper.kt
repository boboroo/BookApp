package com.shiny.bookapp.presentation.mapper

import com.shiny.bookapp.domain.entities.SearchEntity
import com.shiny.bookapp.presentation.search.BookUIModel

fun SearchEntity.toBookUIModelList(): List<BookUIModel> {
    val list = ArrayList<BookUIModel>()
    searchedList.forEach { book ->
        list.add(
            BookUIModel(
                book.title,
                book.contents,
                book.url,
                book.isbn,
                book.dateTime,
                book.authors,
                book.publisher,
                book.translators,
                book.price,
                book.salePrice,
                book.thumbnail,
                book.status
            )
        )
    }
    return list
}