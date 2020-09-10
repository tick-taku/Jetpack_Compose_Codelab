package com.example.jetpackcompose.codelab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class MainViewModel: ViewModel() {

    private val _items: MutableLiveData<MutableList<String>> = MutableLiveData()
    val items: LiveData<List<String>> = _items.map { it.toList() }

    init {
        _items.postValue((0 until 100).map { it.toString() }.toMutableList())
    }

    fun click(index: Int) {
        _items.value?.let {
            it[index] = "Clicked!"
            _items.postValue(it)
        }
    }
}