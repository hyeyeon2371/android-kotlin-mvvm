package com.example.hyeyeon.androidkotlinmvvm.data.service

import com.example.hyeyeon.androidkotlinmvvm.model.GithubRepoReponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author HyeyeonPark
 */
interface SearchDataSource {
    @GET("search/repositories")
    fun getGithubRepoAsync(@Query("q") keyword: String,
                           @Query("sort") sort: String,
                           @Query("order") order: String): Deferred<GithubRepoReponse>

}



