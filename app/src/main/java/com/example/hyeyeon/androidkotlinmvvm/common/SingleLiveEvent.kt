package com.example.hyeyeon.androidkotlinmvvm.common

import android.arch.lifecycle.MutableLiveData

class SingleLiveEvent : MutableLiveData<Boolean>() {
    fun call() = this.postValue(true)
}