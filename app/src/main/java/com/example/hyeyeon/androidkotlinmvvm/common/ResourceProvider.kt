package com.example.hyeyeon.androidkotlinmvvm.common

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build

class ResourceProvider(val context: Context) {
    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getDrawable(redId: Int): Drawable? {
        return context.getDrawable(redId)
    }
}