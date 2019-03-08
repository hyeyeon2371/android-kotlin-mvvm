package com.example.hyeyeon.androidkotlinmvvm.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

open class BaseObservableViewModel : ViewModel(), Observable, LifecycleObserver {
    private var cb: PropertyChangeRegistry? = null
        get() = field ?: PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        cb?.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        cb?.add(callback)
    }
}