package com.example.hyeyeon.androidkotlinmvvm.data.repository


import com.example.hyeyeon.androidkotlinmvvm.data.service.SearchDataSource
import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponseItem
import kotlinx.coroutines.*

interface SearchRepository {
    fun getPeople(NAVER_CLIENT_ID: String, NAVER_CLIENT_SECRET: String, query: String, start: Int, display: Int) : Deferred<List<SearchResponseItem>>
}

class SearchRepositoryImpl(val dataSource: SearchDataSource) : SearchRepository {
    private val itemCache = arrayListOf<SearchResponseItem>()

    override fun getPeople(NAVER_CLIENT_ID: String, NAVER_CLIENT_SECRET: String, query: String, start : Int, display: Int): Deferred<List<SearchResponseItem>> = GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
        val response = dataSource.getSearchResult(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, query, start, display).await()
        itemCache.addAll(response.items)
        response.items
    })

}