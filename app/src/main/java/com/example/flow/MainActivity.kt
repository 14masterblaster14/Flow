package com.example.flow

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * A flow is a coroutine that can emit multiple values sequentially i.e stream of data.
 * It follows Producer, Consumer design pattern.
 * Flow also called as cold state flow as it won't start producing values unless someone won't start collecting it.
 *
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myFlow  = flow<Int> {  // Producer
            for (i in 1..100){
                emit(i)
                delay(1000L)
            }
        }

        val textView = findViewById<TextView>(R.id.tvResult)

        CoroutineScope(Dispatchers.Main).launch {   // Consumer
            myFlow.collect{
                Log.i("MYTAG", "Current index is $it")
                textView.text = "Current index is $it"
            }
        }
    }
}