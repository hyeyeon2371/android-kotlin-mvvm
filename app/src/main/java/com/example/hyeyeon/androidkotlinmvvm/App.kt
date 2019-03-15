package com.example.hyeyeon.androidkotlinmvvm

import android.app.Application
import com.example.hyeyeon.androidkotlinmvvm.module.appModule
import com.example.hyeyeon.androidkotlinmvvm.module.networkModule
import org.koin.android.ext.android.startKoin

/**
 * @author HyeyeonPark
 */
open class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, networkModule))
    }
}