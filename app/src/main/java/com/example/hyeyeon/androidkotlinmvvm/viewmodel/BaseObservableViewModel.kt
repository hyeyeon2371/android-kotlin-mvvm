package com.example.hyeyeon.androidkotlinmvvm.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

open class BaseObservableViewModel : ViewModel(), Observable, LifecycleObserver {
    private var cb: PropertyChangeRegistry? = null

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        if (cb == null) {
            cb = PropertyChangeRegistry()
        }

        cb!!.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        if (cb == null) {
            cb = PropertyChangeRegistry()
        }

        cb!!.add(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (cb == null) {
                return
            }
        }
        cb!!.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId : Int){
        synchronized(this){
            if(cb == null){
                return
            }
        }
        cb!!.notifyCallbacks(this, fieldId, null)
    }
}