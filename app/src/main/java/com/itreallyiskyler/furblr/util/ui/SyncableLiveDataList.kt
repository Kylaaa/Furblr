package com.itreallyiskyler.furblr.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class SynchronizedLiveDataList<T> (defaultData : List<T>) {
    private var _coreData : MutableList<T> = defaultData.toMutableList()
    private val _data = MutableLiveData<List<T>>()
    val liveData : LiveData<List<T>>
        get() = _data

    private fun syncData() {
        _data.postValue(_coreData)
        synchronized(liveData) {
            liveData.notifyAll()
        }
    }
    fun loadData(updatedData : List<T>) : Unit {
        _coreData = updatedData.toMutableList()
        syncData()
    }
    fun reloadDataEntry(index : Int, entry : T) : Unit {
        _coreData[index] = entry
        syncData()
    }
}