package com.example.hyeyeon.androidkotlinmvvm.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.hyeyeon.androidkotlinmvvm.model.history.SearchHistory
import com.example.hyeyeon.androidkotlinmvvm.model.history.SearchHistoryDao

@Database(entities = [SearchHistory::class], version = 1)
abstract class SearchHistoryDB : RoomDatabase() {
    abstract fun dao(): SearchHistoryDao

    companion object {
        fun getInstance(context: Context): SearchHistoryDB {
            return Room.databaseBuilder(context.applicationContext, SearchHistoryDB::class.java, "history.db").fallbackToDestructiveMigration().build()
        }
    }
}