package com.shiny.bookapp.data.repository

import com.shiny.bookapp.data.datasources.SearchBookDataSource
import com.shiny.bookapp.data.mapper.toDomainModel
import com.shiny.bookapp.domain.entities.SearchEntity
import com.shiny.bookapp.domain.extension.mapNetworkResult
import com.shiny.bookapp.domain.network.ResultData
import com.shiny.bookapp.domain.repository.SearchBookRepository

class SearchBookRepositoryImpl(val searchBookDataSource: SearchBookDataSource) : SearchBookRepository {

    override suspend fun getSearchedItems(
        keyword: String,
        pageCount: Int
    ): ResultData<SearchEntity> {

        return searchBookDataSource.getResponse(
            query = keyword,
            size = pageCount
        ).mapNetworkResult { response ->
            response.toDomainModel()
        }
    }

}