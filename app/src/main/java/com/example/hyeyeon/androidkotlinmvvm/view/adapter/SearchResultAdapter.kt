package com.example.hyeyeon.androidkotlinmvvm.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hyeyeon.androidkotlinmvvm.BR
import com.example.hyeyeon.androidkotlinmvvm.R
import com.example.hyeyeon.androidkotlinmvvm.databinding.ItemSearchBinding
import com.example.hyeyeon.androidkotlinmvvm.model.GithubRepoReponse
import com.example.hyeyeon.androidkotlinmvvm.viewmodel.SearchViewModel

/**
 * @author HyeyeonPark
 */
class SearchResultAdapter(private val searchViewModel: SearchViewModel) : RecyclerView.Adapter<SearchResultAdapter.Holder>() {
    private var repoList = mutableListOf<GithubRepoReponse.GithubRepoInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        DataBindingUtil.inflate<ItemSearchBinding>(LayoutInflater.from(parent.context), R.layout.item_search, parent, false).apply {
            this.viewModel = searchViewModel
        }.let { binding ->
            return Holder(binding)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    fun setItem(list: MutableList<GithubRepoReponse.GithubRepoInfo>?) {
        this.repoList = list ?: mutableListOf()
        notifyDataSetChanged()
    }

    fun clearItem() {
        repoList.clear()
        notifyDataSetChanged()
    }
    fun addAllItems(list: MutableList<GithubRepoReponse.GithubRepoInfo>) {
        this.repoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepoReponse.GithubRepoInfo) = binding.setVariable(BR.item, item)
    }
}