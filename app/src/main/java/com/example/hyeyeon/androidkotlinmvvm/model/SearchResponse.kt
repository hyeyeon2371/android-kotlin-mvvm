package com.example.hyeyeon.androidkotlinmvvm.model

import java.util.*

/**
 * @author HyeyeonPark
 */
class SearchResponse {
    lateinit var lastBuildDate: String
    lateinit var items : MutableList<SearchResponseItem>

    var total: Int = 0
    var start: Int = 1
    var display = 10
}

/**
 * @author HyeyeonPark
 */
class SearchResponseItem : Observable() {
    lateinit var title: String
    lateinit var link: String
    lateinit var description: String
    lateinit var image: String
}