package com.shiny.bookapp.data.di

import com.shiny.bookapp.data.api.SearchApi
import com.shiny.bookapp.data.datasources.RemoteSearchBookDataSource
import com.shiny.bookapp.data.datasources.SearchBookDataSource
import com.shiny.bookapp.data.repository.SearchBookRepositoryImpl
import com.shiny.bookapp.domain.repository.SearchBookRepository
import com.shiny.bookapp.domain.usecases.GetSearchedBooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Singleton
    @Provides
    fun provideSearchDataSource(searchApi: SearchApi) : SearchBookDataSource {
        return RemoteSearchBookDataSource(searchApi)
    }

    @Singleton
    @Provides
    fun provideSearchBookRepository(bookDataSource: SearchBookDataSource) : SearchBookRepository {
        return SearchBookRepositoryImpl(bookDataSource)
    }

    @Singleton
    @Provides
    fun provideGetSearchedBooksUseCase(repository: SearchBookRepository): GetSearchedBooksUseCase {
        return GetSearchedBooksUseCase(repository)
    }

}