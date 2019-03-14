package com.example.hyeyeon.androidkotlinmvvm.model.history

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.hyeyeon.androidkotlinmvvm.common.base.BaseDao

@Dao
interface SearchHistoryDao : BaseDao<SearchHistory> {
    @Query("SELECT * FROM history")
    fun selectAllHistories(): List<SearchHistory>

    @Query("SELECT * FROM history WHERE id = :id")
    fun selectHistory(id: Long): List<SearchHistory>
}