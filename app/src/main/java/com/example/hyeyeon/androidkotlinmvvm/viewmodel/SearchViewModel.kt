package com.example.hyeyeon.androidkotlinmvvm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
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


class SearchViewModel(private val handler: SearchEventHandler, private val repository: SearchRepositoryImpl, resourceProvider: ResourceProvider) : BaseObservableViewModel() {
    private val logger = Logger.getLogger(this::class.java.name)!!
    private val NAVER_CLIENT_ID: String = resourceProvider.getString(R.string.naver_client_id)
    private val NAVER_CLIENT_SECRET: String = resourceProvider.getString(R.string.naver_client_secret)
    private var display = 15

    var page = 0
        set(value) {
            field = value
            pageStr.set(Integer.toString(value))
        }

    var pageStr = ObservableField<String>(Integer.toString(page))
        @Bindable
        get() = field

    var query: String = ""

    var searchResultList = MutableLiveData<List<SearchResponseItem>>().apply { ArrayList<SearchResponseItem>() }

    var historyVisibility = ObservableInt(View.GONE)
        @Bindable
        get() = field

    fun onClickItem(message: String) = handler.onClickItem(message)

    fun onClickSearch() {
        page = 0
        searchResultList.postValue(null)
        getSearchResults()
    }

    fun getSearchResults() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            try {
                (display * (++page - 1) + 1).let { offset ->
                    repository.getSearchResultAsync(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, query, offset, display).await().let { list ->
                        searchResultList.postValue(list)
                    }
                }
            } catch (t: Throwable) {
                logger.warning(t.localizedMessage)
            }
        })
    }

    fun onClickHistory() {
        historyVisibility = if (historyVisibility.get() == View.VISIBLE) {
            ObservableInt(View.GONE)
        } else {
            ObservableInt(View.VISIBLE)
        }

        historyVisibility.notifyChange()
    }

}