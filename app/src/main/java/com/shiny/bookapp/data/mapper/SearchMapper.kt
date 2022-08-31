package com.shiny.bookapp.data.mapper

import com.shiny.bookapp.data.models.BookResponse
import com.shiny.bookapp.data.models.SearchResponse
import com.shiny.bookapp.domain.entities.BookEntity
import com.shiny.bookapp.domain.entities.SearchEntity

fun SearchResponse<BookResponse>.toDomainModel(): SearchEntity {
    val list = ArrayList<BookEntity>()
    documents?.forEach { book ->
        list.add(
            BookEntity(
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

    return SearchEntity(meta?.isEnd ?: true, list)
}