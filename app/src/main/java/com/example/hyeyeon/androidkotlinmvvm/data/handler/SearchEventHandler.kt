package com.example.hyeyeon.androidkotlinmvvm.data.handler

import com.example.hyeyeon.androidkotlinmvvm.model.history.SearchHistory

interface SearchEventHandler : BaseEventHandler{
    fun onClickItem(message: String)
    fun insertSearchHistory(searchHistory: SearchHistory)
}