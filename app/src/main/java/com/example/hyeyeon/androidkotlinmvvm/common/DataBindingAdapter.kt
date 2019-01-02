package com.example.hyeyeon.androidkotlinmvvm.common

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.hyeyeon.androidkotlinmvvm.model.SearchResponseItem
import com.example.hyeyeon.androidkotlinmvvm.view.adapter.SearchAdapter


object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:searchItem")
    fun RecyclerView.bindItem(items: MutableList<SearchResponseItem>?) {
        if (items != null) {
            val adapter = this.adapter as SearchAdapter
            adapter.setItem(items)
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun ImageView.loadUrl(url: String?) {
        if(url != null){
            Glide.with(this.context).load(url).thumbnail(0.3f).into(this)
        }
    }
}