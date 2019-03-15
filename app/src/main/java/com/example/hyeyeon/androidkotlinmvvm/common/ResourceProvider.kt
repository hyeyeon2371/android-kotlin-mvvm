package com.example.hyeyeon.androidkotlinmvvm.common

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build

/**
 * @author HyeyeonPark
 */
class ResourceProvider(private val context: Context) {
    fun getString(resId: Int): String = context.getString(resId)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getDrawable(redId: Int): Drawable? = context.getDrawable(redId)
}