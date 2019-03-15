package com.example.hyeyeon.androidkotlinmvvm.data.service

import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author HyeyeonPark
 */
interface SearchDataSource {
    /* GET */
    @GET("book.json")
    fun getSearchResultAsync(@Header("X-Naver-Client-Id") clientId: String,
                             @Header("X-Naver-Client-Secret") clientSecret: String,
                             @Query("query") query: String,
                             @Query("start") start: Int,
                             @Query("display") display: Int): Deferred<SearchResponse>

}



