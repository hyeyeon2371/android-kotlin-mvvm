package com.example.hyeyeon.androidkotlinmvvm.model.keyword

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.hyeyeon.androidkotlinmvvm.common.base.BaseDao

/**
 * @author HyeyeonPark
 */
@Dao
interface SearchKeywordDao : BaseDao<SearchKeyword> {
    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 5")
    fun selectAllHistories(): List<SearchKeyword>

    @Query("SELECT * FROM history WHERE id = :id ")
    fun selectHistory(id: Long): List<SearchKeyword>
}