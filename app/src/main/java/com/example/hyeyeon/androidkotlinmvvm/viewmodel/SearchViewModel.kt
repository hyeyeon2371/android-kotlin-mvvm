package com.example.hyeyeon.androidkotlinmvvm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.example.hyeyeon.androidkotlinmvvm.BR
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.common.ResourceProvider
import com.example.hyeyeon.androidkotlinmvvm.common.base.BaseObservableViewModel
import com.example.hyeyeon.androidkotlinmvvm.data.handler.SearchEventHandler
import com.example.hyeyeon.androidkotlinmvvm.data.repository.SearchRepositoryImpl
import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponseItem
import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeyword
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Logger

class SearchViewModel(private val handler: SearchEventHandler, private val repository: SearchRepositoryImpl, resourceProvider: ResourceProvider) : BaseObservableViewModel() {
    private val logger = Logger.getLogger(this::class.java.name)!!
    private val NAVER_CLIENT_ID: String = resourceProvider.getString(R.string.naver_client_id)
    private val NAVER_CLIENT_SECRET: String = resourceProvider.getString(R.string.naver_client_secret)
    private var DISPLAY = 15

    private var page = 0
        set(value) {
            field = value
            pageStr.set(Integer.toString(value))
        }

    var pageStr = ObservableField<String>(Integer.toString(page))
        @Bindable
        get() = field

    var keyword: String = ""

    var searchResultList = MutableLiveData<List<SearchResponseItem>>().apply { ArrayList<SearchResponseItem>() }
    var searchKeywordList = MutableLiveData<MutableList<SearchKeyword>>().apply { ArrayList<SearchKeyword>() }

    var historyEmptyViewVisibility = ObservableInt(View.GONE)
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.historyEmptyViewVisibility)
        }

    var historyVisibility = ObservableInt(View.GONE)
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.historyVisibility)
        }

    var resultEmptyViewVisibility = ObservableInt(View.VISIBLE)
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.resultEmptyViewVisibility)
        }

    fun onClickItem(message: String) = handler.showMessage(message)

    fun onClickSearch() {
        page = 0
        searchResultList.postValue(null)
        getSearchResults()
        handler.insertSearchHistory(SearchKeyword(keyword = keyword))
    }

    fun getSearchResults() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            try {
                (DISPLAY * (++page - 1) + 1).let { offset ->
                    repository.getSearchResultAsync(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, keyword, offset, DISPLAY).await().let { list ->
                        searchResultList.postValue(list)
                    }
                }
            } catch (t: Throwable) {
                logger.warning(t.message)
            }
        })
    }

    fun onClickHistory() {
        historyVisibility = if (historyVisibility.get() == View.VISIBLE) {
            ObservableInt(View.GONE)
        } else {
            ObservableInt(View.VISIBLE)
        }
        notifyPropertyChanged(BR.historyVisibility)
    }

    fun onClickDeleteKeyword(searchKeyword: SearchKeyword) {
        handler.deleteSearchHistory(searchKeyword)
    }

}