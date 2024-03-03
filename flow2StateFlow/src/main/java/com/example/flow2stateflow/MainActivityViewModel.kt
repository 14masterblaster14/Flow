package com.example.flow2stateflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainActivityViewModel(startingTotal: Int) : ViewModel() {

    /*
    // Android Live Data
    private var _total = MutableLiveData<Int>()
    val totalData : LiveData<Int>
        get() = _total

     */

    // Kotlin StateFlow i.e. Producers
    private val _flowTotal = MutableStateFlow<Int>(0) // It will always has initial value
    val flowTotal: StateFlow<Int> = _flowTotal
    //get()= _flowTotal

    // Kotlin SharedFlow
    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message

    init {
        // Android Live Data
        //_total.value = startingTotal

        // Kotlin StateFlow
        _flowTotal.value = startingTotal
    }

    fun setTotal(input: Int) {

        // Android Live Data
        //_total.value = (_total.value)?.plus(input)

        // Kotlin StateFlow
        _flowTotal.value = (_flowTotal.value).plus(input)

        //Kotlin SharedFlow
        viewModelScope.launch {
            _message.emit("Total Updated Successfully")
        }
    }
}