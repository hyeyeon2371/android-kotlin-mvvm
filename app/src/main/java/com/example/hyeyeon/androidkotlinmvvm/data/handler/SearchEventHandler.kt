package com.example.hyeyeon.androidkotlinmvvm.data.handler

import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeyword

/**
 * @author HyeyeonPark
 */
interface SearchEventHandler : BaseEventHandler {
    fun insertSearchHistory(searchKeyword: SearchKeyword)
    fun deleteSearchHistory(searchKeyword: SearchKeyword)
}