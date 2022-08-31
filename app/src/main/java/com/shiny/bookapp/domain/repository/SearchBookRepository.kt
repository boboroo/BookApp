package com.shiny.bookapp.domain.repository

import com.shiny.bookapp.domain.entities.SearchEntity
import com.shiny.bookapp.domain.network.ResultData

interface SearchBookRepository {
    suspend fun getSearchedItems(
        keyword: String,
        pageCount: Int
    ): ResultData<SearchEntity>
}