package com.example.hyeyeon.androidkotlinmvvm.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.databinding.ItemHistoryBinding
import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeyword
import com.example.hyeyeon.androidkotlinmvvm.viewmodel.SearchViewModel

class SearchKeywordAdapter(val viewModel: SearchViewModel) : RecyclerView.Adapter<SearchKeywordAdapter.Holder>() {
    private var items: MutableList<SearchKeyword> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(LayoutInflater.from(parent.context), R.layout.item_history, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun setItem(items: MutableList<SearchKeyword>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: SearchKeyword) {
            binding.keyword = keyword
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}