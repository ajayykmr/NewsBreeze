package com.example.newsbreezeajaykumar.api

import com.example.newsbreezeajaykumar.models.ApiRespone
import com.example.newsbreezeajaykumar.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "in",
/*
        @Query("page")
        pageNumber: Int = 1,
*/
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<ApiRespone>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
/*
        @Query("page")
        pageNumber: Int = 1,
*/
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<ApiRespone>
}