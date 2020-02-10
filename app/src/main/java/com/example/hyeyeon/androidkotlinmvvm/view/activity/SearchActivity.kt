package com.example.hyeyeon.androidkotlinmvvm.view.activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableInt
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.data.handler.SearchEventHandler
import com.example.hyeyeon.androidkotlinmvvm.data.room.SearchKeywordDB
import com.example.hyeyeon.androidkotlinmvvm.databinding.ActivitySearchBinding
import com.example.hyeyeon.androidkotlinmvvm.model.GithubRepoReponse
import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeyword
import com.example.hyeyeon.androidkotlinmvvm.view.adapter.SearchKeywordAdapter
import com.example.hyeyeon.androidkotlinmvvm.view.adapter.SearchResultAdapter
import com.example.hyeyeon.androidkotlinmvvm.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * @author HyeyeonPark
 */
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var handler: SearchEventHandler
    private lateinit var searchKeywordDB: SearchKeywordDB
    private val viewModel: SearchViewModel by viewModel { parametersOf(handler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initEventHandlerAndObserver()
        initRoomDB()
        initSearchResultListView()
        initSearchKeywordListView()

        binding.clSearchHistory.bringToFront()
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun initEventHandlerAndObserver() {
        handler = object : SearchEventHandler {
            override fun showMessage(message: String) = Toast.makeText(this@SearchActivity, message, Toast.LENGTH_SHORT).show()
            override fun insertSearchHistory(searchKeyword: SearchKeyword) {
                CoroutineScope(Dispatchers.IO).launch {
                    searchKeywordDB.dao().insertEntity(searchKeyword)
                    viewModel.searchKeywordList.postValue(searchKeywordDB.dao().selectAllHistories().toMutableList())
                }
                hideKeyboard()
                viewModel.historyVisibility = ObservableInt(View.GONE)
            }

            override fun deleteSearchHistory(searchKeyword: SearchKeyword) {
                CoroutineScope(Dispatchers.IO).launch {
                    searchKeywordDB.dao().deleteEntity(searchKeyword)
                    viewModel.searchKeywordList.postValue(searchKeywordDB.dao().selectAllHistories().toMutableList())
                }
            }
        }

        Observer<MutableList<SearchKeyword>> {
            if (it != null) {
                (binding.rvSearchHistory.adapter as SearchKeywordAdapter).setItem(it)
                viewModel.historyEmptyViewVisibility = if (it.isEmpty()) {
                    ObservableInt(View.VISIBLE)
                } else {
                    ObservableInt(View.GONE)
                }
            }
        }.let { keywordObserver ->
            viewModel.searchKeywordList.observe(this, keywordObserver)
        }

        Observer<MutableList<GithubRepoReponse.GithubRepoInfo>> {
            if (it.isNullOrEmpty()) {
                (binding.rvSearchResult.adapter as SearchResultAdapter).clearItem()
                viewModel.resultEmptyViewVisibility = ObservableInt(View.VISIBLE)
            } else {
                (binding.rvSearchResult.adapter as SearchResultAdapter).setItem(it)
                viewModel.resultEmptyViewVisibility = ObservableInt(View.GONE)
            }
        }.let { resultObserver ->
            viewModel.githubRepoList.observe(this, resultObserver)
        }
    }

    private fun initSearchResultListView() {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager!! as LinearLayoutManager).let { layoutManager ->
                    val totalItemCount = layoutManager.itemCount
                    val lastItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (totalItemCount - 1 == lastItemPosition)
                        viewModel.getSearchResults()
                }
            }
        }
//        binding.rvSearchResult.addOnScrollListener(listener)

        binding.rvSearchResult.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSearchResult.adapter = SearchResultAdapter(viewModel)
    }

    private fun initRoomDB() {
        searchKeywordDB = SearchKeywordDB.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.searchKeywordList.postValue(searchKeywordDB.dao().selectAllHistories().toMutableList())
        }
    }

    private fun initSearchKeywordListView() {
        binding.rvSearchHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSearchHistory.adapter = SearchKeywordAdapter(viewModel)
    }

    private fun hideKeyboard() {
        val manager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.etSearchKeyword.windowToken, 0)
    }
}
