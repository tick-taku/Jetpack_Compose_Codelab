package com.example.jetpackcompose.codelab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.jetpackcompose.codelab.util.doOnNext

class MainViewModel: ViewModel() {

    companion object {
        private const val ITEM_COUNT = 100
    }

    private val _items: MutableLiveData<MutableList<Boolean>> = MutableLiveData()
    val items: LiveData<List<Boolean>> =
        _items.map { it.toList() }
            .doOnNext {
                _openDialog.postValue(it.all { isChecked -> isChecked })
            }

    private val _openDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val openDialog: LiveData<Boolean> = _openDialog

    init { refresh() }

    fun click(index: Int) {
        _items.value?.let {
            it[index] = true
            _items.postValue(it)
        }
    }

    fun refresh() {
        _items.postValue(MutableList(ITEM_COUNT) { false })
    }
}