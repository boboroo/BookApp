package com.shiny.bookapp.data.datasources

import com.shiny.bookapp.data.models.BookResponse
import com.shiny.bookapp.data.models.SearchResponse
import com.shiny.bookapp.domain.network.ResultData

// book, image 등 API/기능이 추가될 때마다 인터페이스가 비대해지지 않도록, book search에 대한 local/remote 추상화 용도로만 쓴다.
interface SearchBookDataSource {

    suspend fun getResponse(
        query: String,
        size: Int? = null
    ): ResultData<SearchResponse<BookResponse>>

}