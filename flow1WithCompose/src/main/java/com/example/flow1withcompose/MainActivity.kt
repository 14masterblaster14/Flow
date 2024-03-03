package com.example.flow1withcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flow1withcompose.ui.theme.FlowTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 *  In DataStream, if data is produced faster than data being consumed then it's called as BackPressure.
 *  But Kotlin Flow API is created in a way that it will produce next item only when
 *  it's earlier item is consumed.
 *  Flow Operators : (https://kotlinlang.org/docs/flow.html#intermediate-flow-operators)
 *
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        val myFlow = flow{
//            for (i in 1..100) {
//                emit(i)
//                delay(1000L)
//            }
//        }

        setContent {
            // With local myFlow
            //val currentValue = myFlow.collectAsState(initial = 1).value

            // With ViewModel myFlow
            val viewModel = viewModel<MyViewModel>()
            val currentValue = viewModel.myFlow.collectAsState(initial = 1).value

            FlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        text = "Current index is $currentValue",
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}

