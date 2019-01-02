package com.example.hyeyeon.androidkotlinmvvm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.databinding.ObservableField
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.common.ResourceProvider
import com.example.hyeyeon.androidkotlinmvvm.data.handler.SearchEventHandler
import com.example.hyeyeon.androidkotlinmvvm.data.repository.SearchRepositoryImpl
import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponseItem
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Logger


class SearchViewModel(val handler: SearchEventHandler, private val repository: SearchRepositoryImpl, resourceProvider: ResourceProvider) : BaseObservableViewModel() {
    private val logger = Logger.getLogger(this::class.java.name)!!
    private val NAVER_CLIENT_ID: String = resourceProvider.getString(R.string.NaverClientID)
    private val NAVER_CLIENT_SECRET: String = resourceProvider.getString(R.string.NaverClientSecret)

    private var display = 5

    var page = 0
        set(value) {
            field = value
            pageStr.set(Integer.toString(value))
        }

    var pageStr = ObservableField<String>(Integer.toString(page))
        @Bindable
        get() = field

    var query: String = ""
        set(value) {
            field = value
            page = 0

            searchResults.postValue(null)
            if(!value.isBlank()){
                getSearchResults()
            }
        }

    var searchResults = MutableLiveData<List<SearchResponseItem>>().apply { ArrayList<SearchResponseItem>() }

    fun onClickItem(message: String) {
        handler.onClickItem(message)
    }

    fun getSearchResults() {
        val offset = display * (++page - 1) + 1
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            try{
                val items = repository.getPeople(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, query, offset, display).await()
                searchResults.postValue(items)
            }catch (t: Throwable){
                logger.warning(t.localizedMessage)
            }
        })
    }

}