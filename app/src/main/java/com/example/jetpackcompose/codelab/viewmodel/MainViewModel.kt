package com.example.jetpackcompose.codelab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.jetpackcompose.codelab.util.doOnNext

class MainViewModel: ViewModel() {

    companion object {
        private const val ITEM_COUNT = 100
        private const val CHECK_MARK = "Clicked!"
    }

    private val _items: MutableLiveData<MutableList<String>> = MutableLiveData()
    val items: LiveData<List<String>> =
        _items.map { it.toList() }
            .doOnNext {
                _openDialog.postValue(it.all { item -> item == CHECK_MARK })
            }

    private val _openDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val openDialog: LiveData<Boolean> = _openDialog

    init { refresh() }

    fun click(index: Int) {
        _items.value?.let {
            it[index] = CHECK_MARK
            _items.postValue(it)
        }
    }

    fun refresh() {
        _items.postValue((0 until ITEM_COUNT).map { it.toString() }.toMutableList())
    }
}