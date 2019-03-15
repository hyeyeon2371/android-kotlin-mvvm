package com.example.hyeyeon.androidkotlinmvvm.common.base

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

/**
 * @author HyeyeonPark
 */
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntity(vararg entity: T)

    @Delete
    fun deleteEntity(vararg entity: T)


}