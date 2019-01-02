package com.example.hyeyeon.androidkotlinmvvm.data.handler

interface SearchEventHandler : BaseEventHandler{
    fun onClickItem(message: String)
}