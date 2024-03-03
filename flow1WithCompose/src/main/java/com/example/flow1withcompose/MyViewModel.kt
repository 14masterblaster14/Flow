package com.example.flow1withcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    // Producer
    val myFlow = flow {
        for (i in 1..100) {
            emit(i)
            delay(1000L)
        }
    }

    init {
        // Trying to create Backpressure but Flow API won't create it
        backPressureDemo()
    }

    //Producer
    private fun backPressureDemo() {
        val myFlow1 = flow {
            for (i in 1..100) {
                Log.i("MYTAG", "Produced $i")
                emit(i)
                delay(1000L)
            }
        }

        // Normal Consumer
        viewModelScope.launch {
            myFlow1.collect {
                delay(2000L) // It will try to create BackPressure but won't
                Log.i("MYTAG", "Consumed $it")
            }
        }
        /*  Result: P1,C1,P2,C2,P3,C3....P100,C100  */


        //  Consumer with Buffer operator (It will create separate co-routine for consumer to run and collect)
        viewModelScope.launch {
            myFlow1.buffer().collect {
                delay(2000L) // It will create BackPressure
                Log.i("MYTAG", "Consumed $it")
            }
        }
        /*  Result: P1,P2,C1,P3,P4,C2...P98,P99,C56,P100,C57,C58..C100  */


        //  Consumer with Latest operator (to collect last value)
        viewModelScope.launch {
            myFlow1.collectLatest {
                delay(2000L) // It will create BackPressure
                Log.i("MYTAG", "Consumed $it")
            }
        }
        /*  Result: P1,P2,P3,P4....P100,C100  */

        // Map Operator
        viewModelScope.launch {
            myFlow1
                .map { it ->
                    showMessage(it)
                }.collect {
                    Log.i("MYTAG", "Consumed $it")
                }
        }
        /*  Result: P1,C Hello 1,P2,C Hello 2 ....P100,C Hello 100  */

        // Filter Operator
        viewModelScope.launch {
            myFlow1
                .filter { i ->
                    i % 3 == 0
                }
                .map { it ->                    // This is Operator chaining
                    showMessage(it)
                }.collect {
                    Log.i("MYTAG", "Consumed $it")
                }
        }
    }
    /*  Result: P1,P2,P3,C Hello 3,P4,P5,P6,C Hello 6 ....P97,P98,P99,C Hello 99,P100  */


    private fun showMessage(count: Int): String {
        return "Hello $count"
    }
}