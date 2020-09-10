package com.example.jetpackcompose.codelab.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

inline fun <T> LiveData<T>.doOnNext(crossinline onNext: (T) -> Unit): LiveData<T> {
    val next = MediatorLiveData<T>()
    next.addSource(this) {
        if (it != null) {
            onNext(it)
            next.value = it
        }
    }
    return next
}
