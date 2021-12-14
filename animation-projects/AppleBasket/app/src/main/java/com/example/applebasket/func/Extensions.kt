package com.example.applebasket.func

import androidx.lifecycle.MutableLiveData

object Extensions {
    // Расширения для обновления LiveData
    fun <T> MutableLiveData<ArrayList<T>>.addAndUpdate(item: T) {
        val value = this.value
        value?.add(item)
        this.value = value
    }

    fun <T> MutableLiveData<ArrayList<T>>.clearAndUpdate() {
        val value = this.value
        value?.clear()
        this.value = value
    }
}