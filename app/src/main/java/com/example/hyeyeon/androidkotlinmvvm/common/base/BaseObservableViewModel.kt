package com.example.hyeyeon.androidkotlinmvvm.common.base

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

/**
 * @author HyeyeonPark
 */
open class BaseObservableViewModel : ViewModel(), Observable, LifecycleObserver {
    private var registry: PropertyChangeRegistry? = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry?.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry?.add(callback)
    }

    fun notifyChanged() {
        registry?.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(propertyId: Int) {
        registry?.notifyCallbacks(this, propertyId, null)
    }
}