package com.example.hyeyeon.androidkotlinmvvm.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeyword
import com.example.hyeyeon.androidkotlinmvvm.model.keyword.SearchKeywordDao

@Database(entities = [SearchKeyword::class], version = 1)
abstract class SearchKeywordDB : RoomDatabase() {
    abstract fun dao(): SearchKeywordDao

    companion object {
        fun getInstance(context: Context): SearchKeywordDB {
            return Room.databaseBuilder(context.applicationContext, SearchKeywordDB::class.java, "history.db").fallbackToDestructiveMigration().build()
        }
    }
}