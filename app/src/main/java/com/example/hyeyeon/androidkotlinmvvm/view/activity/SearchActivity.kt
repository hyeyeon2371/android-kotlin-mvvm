package com.example.hyeyeon.androidkotlinmvvm.view.activity


import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.data.handler.SearchEventHandler
import com.example.hyeyeon.androidkotlinmvvm.databinding.ActivitySearchBinding
import com.example.hyeyeon.androidkotlinmvvm.model.history.SearchHistory
import com.example.hyeyeon.androidkotlinmvvm.data.room.SearchHistoryDB
import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponseItem
import com.example.hyeyeon.androidkotlinmvvm.view.adapter.SearchAdapter
import com.example.hyeyeon.androidkotlinmvvm.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var handler: SearchEventHandler
    private lateinit var searchHistoryDB: SearchHistoryDB
    private val viewModel: SearchViewModel by viewModel { parametersOf(handler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initRoomDB()
        initEventHandlerAndObserver()
        initSearchListView()

        binding.llSearchHistory.bringToFront()
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun initEventHandlerAndObserver() {
        handler = object : SearchEventHandler {
            override fun onClickItem(message: String) = Toast.makeText(this@SearchActivity, message, Toast.LENGTH_SHORT).show()
            override fun showMessage(message: String) = Toast.makeText(this@SearchActivity, message, Toast.LENGTH_SHORT).show()
            override fun insertSearchHistory(searchHistory: SearchHistory) {
                CoroutineScope(Dispatchers.IO).launch {
                    searchHistoryDB.dao().insertEntity(searchHistory)
                }
            }
        }

        Observer<List<SearchResponseItem>> {
            if (it.isNullOrEmpty()) {
                (binding.rvSearchList.adapter as SearchAdapter).clearItem()
            } else {
                (binding.rvSearchList.adapter as SearchAdapter).addAllItems(it)
            }
        }.let { observer ->
            viewModel.searchResultList.observe(this, observer)
        }
    }

    private fun initSearchListView() {
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                (recyclerView.layoutManager!! as LinearLayoutManager).let { layoutManager ->
                    val totalItemCount = layoutManager.itemCount
                    val lastItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (totalItemCount - 1 == lastItemPosition)
                        viewModel.getSearchResults()
                }
            }
        }.let { binding.rvSearchList.addOnScrollListener(it) }

        binding.rvSearchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSearchList.adapter = SearchAdapter(viewModel)
    }

    private fun initRoomDB() {
        searchHistoryDB = SearchHistoryDB.getInstance(this)
    }
}
