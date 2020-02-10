package com.example.hyeyeon.androidkotlinmvvm.data.repository

import com.example.hyeyeon.androidkotlinmvvm.data.service.SearchDataSource
import com.example.hyeyeon.androidkotlinmvvm.model.GithubRepoReponse
import kotlinx.coroutines.*

/**
 * @author HyeyeonPark
 */
interface SearchRepository {
    fun getGithubRepoAsync(keyword: String, sort: String, order: String): Deferred<MutableList<GithubRepoReponse.GithubRepoInfo>>
}

/**
 * @author HyeyeonPark
 */
class SearchRepositoryImpl(private val dataSource: SearchDataSource) : SearchRepository {
    override fun getGithubRepoAsync(keyword: String, sort: String, order: String): Deferred<MutableList<GithubRepoReponse.GithubRepoInfo>> = GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
        dataSource.getGithubRepoAsync(keyword = keyword, sort = sort, order = order).await().let {
            it.items
        }
    })
}