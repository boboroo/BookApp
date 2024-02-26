package com.shiny.bookapp.data.datasources

import com.shiny.bookapp.data.api.SearchApi
import com.shiny.bookapp.data.models.BookResponse
import com.shiny.bookapp.data.models.SearchResponse
import com.shiny.bookapp.data.network.networkHandling
import com.shiny.bookapp.domain.network.ResultData

class RemoteSearchBookDataSource(
    private val searchApi: SearchApi
): SearchBookDataSource {

    override suspend fun getResponse(
        query: String,
        size: Int?
    ): ResultData<SearchResponse<BookResponse>> {

        val map = HashMap<String, String>()
        map.put("query", query)
        size?.let { _size -> map.put("size", _size.toString()) }

        return networkHandling { searchApi.searchBooks(map) }
    }
}