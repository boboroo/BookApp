package com.shiny.bookapp.data.api

import com.shiny.bookapp.data.models.BookResponse
import com.shiny.bookapp.data.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchApi {

    @GET("/v3/search/book")
    suspend fun searchBooks(
        @QueryMap options: Map<String, String>
    ): SearchResponse<BookResponse>


    /*@GET("/v3/search/image")
    suspend fun searchImage(
        @QueryMap options: Map<String, String>
    ): SearchResponse<ImageResponse>*/

}