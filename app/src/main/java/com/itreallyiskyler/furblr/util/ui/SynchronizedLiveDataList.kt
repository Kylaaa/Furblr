package com.itreallyiskyler.furblr.util

class SynchronizedLiveDataList<T> (defaultData : List<T>) : SynchronizedLiveData<MutableList<T>>(defaultData.toMutableList()) {

    fun loadData(updatedData : List<T>) : Unit {
        _coreData = updatedData.toMutableList()
        syncData()
    }

    fun reloadDataEntry(index : Int, entry : T) : Unit {
        _coreData[index] = entry
        syncData()
    }
}