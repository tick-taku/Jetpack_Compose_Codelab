package com.example.jetpackcompose.codelab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class MainViewModel: ViewModel() {

    companion object {
        private const val ITEM_COUNT = 100
    }

    private val _items: MutableLiveData<MutableList<String>> = MutableLiveData()
    val items: LiveData<List<String>> = _items.map { it.toList() }

    init { refresh() }

    fun click(index: Int) {
        _items.value?.let {
            it[index] = "Clicked!"
            _items.postValue(it)
        }
    }

    fun refresh() {
        _items.postValue((0 until ITEM_COUNT).map { it.toString() }.toMutableList())
    }
}