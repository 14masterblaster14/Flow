package com.example.flow2stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class MainActivityViewModelFactory(private val startingTotal: Int) : ViewModelProvider.Factory,
    ViewModelStoreOwner {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainActivityViewModel(startingTotal) as T

    override val viewModelStore: ViewModelStore
        get() = TODO("Not yet implemented")
}
