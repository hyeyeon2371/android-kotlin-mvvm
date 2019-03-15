package com.example.hyeyeon.androidkotlinmvvm.model.keyword

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "history", indices = [Index(value = ["keyword"], unique = true)])
data class SearchKeyword(@ColumnInfo val keyword: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
