package com.itreallyiskyler.furblr.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.internal.notifyAll

open class SynchronizedLiveData<T> (defaultData : T) {
    protected var _coreData : T = defaultData
    private val _data = MutableLiveData<T>()
    val liveData : LiveData<T>
        get() = _data

    protected fun syncData() {
        _data.postValue(_coreData!!)
        synchronized(liveData) {
            liveData.notifyAll()
        }
    }
    fun loadData(updatedData : T) : Unit {
        _coreData = updatedData
        syncData()
    }
}