package com.shiny.bookapp.domain.usecases

import com.shiny.bookapp.domain.entities.SearchEntity
import com.shiny.bookapp.domain.network.ResultData
import com.shiny.bookapp.domain.repository.SearchBookRepository

class GetSearchedBooksUseCase(val repository: SearchBookRepository) {

    companion object {
        const val PAGE_LOAD_SIZE = 15
    }

    suspend operator fun invoke(keyword: String): ResultData<SearchEntity> =
        repository.getSearchedItems(keyword, PAGE_LOAD_SIZE)

}