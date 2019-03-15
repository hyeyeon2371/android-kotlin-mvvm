package com.example.hyeyeon.androidkotlinmvvm.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hyeyeon.androidkotlinmvvm.BR
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.databinding.ItemSearchBinding
import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponseItem
import com.example.hyeyeon.androidkotlinmvvm.viewmodel.SearchViewModel

class SearchResultAdapter(private val searchViewModel: SearchViewModel) : RecyclerView.Adapter<SearchResultAdapter.Holder>() {
    private var personList: MutableList<SearchResponseItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        DataBindingUtil.inflate<ItemSearchBinding>(LayoutInflater.from(parent.context), R.layout.item_search, parent, false).apply {
            this.viewModel = searchViewModel
        }.let { binding ->
            return Holder(binding)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun setItem(personList: MutableList<SearchResponseItem>?) {
        if (personList == null) return

        this.personList = personList
        notifyDataSetChanged()
    }

    fun clearItem() {
        personList.clear()
        notifyDataSetChanged()
    }

    fun addAllItems(personList: List<SearchResponseItem>) {
        this.personList.addAll(personList)
        notifyItemRangeInserted(itemCount - personList.size, personList.size)
    }

    inner class Holder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResponseItem) = binding.setVariable(BR.item, item)
    }
}