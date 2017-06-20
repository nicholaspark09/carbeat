package com.example.vn008xw.carbeat.data.vo

import android.arch.lifecycle.LiveData

/**
 * Created by vn008xw on 6/18/17.
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}