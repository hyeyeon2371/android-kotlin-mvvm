package com.example.hyeyeon.androidkotlinmvvm.common

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.hyeyeon.androidkotlinmvvm.model.GithubRepoReponse
import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeyword
import com.example.hyeyeon.androidkotlinmvvm.view.adapter.SearchKeywordAdapter
import com.example.hyeyeon.androidkotlinmvvm.view.adapter.SearchResultAdapter

/**
 * @author HyeyeonPark
 */
object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:resultItem")
    fun RecyclerView.bindSearchItem(items: MutableList<GithubRepoReponse.GithubRepoInfo>?) = items?.let { (this.adapter as SearchResultAdapter).setItem(items) }

    @JvmStatic
    @BindingAdapter("bind:keywordItem")
    fun RecyclerView.bindKeywordItem(items: MutableList<SearchKeyword>?) = items?.let { (this.adapter as SearchKeywordAdapter).setItem(items) }

    @JvmStatic
    @BindingAdapter("android:src")
    fun ImageView.loadUrl(url: String?) = url?.let { Glide.with(this.context).load(url).thumbnail(0.3f).into(this) }
}