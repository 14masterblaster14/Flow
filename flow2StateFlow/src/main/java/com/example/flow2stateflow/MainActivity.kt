package com.example.flow2stateflow

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.flow2stateflow.databinding.ActivityMainBinding
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * StateFlow : It's similar to LiveData. It handles the state.
 *      It will always initializes with initial value so it won't be null.
 *      Hot state flow start producing values immediately without checking if someone is consuming it or not.
 *
 *      LiveData automatically unregister the consumers, when teh view goes to stopped state.
 *      Stateflow does not do that by default, we need to use it with lifecycleScope for that.
 *
 *      LiveData is part of Android Framework. So we can only use the code for Android Development.
 *      StateFlow is a part of Kotlin language. So we can use it for Kotlin Multiplatform projects.
 *
 * SharedFlow : It's similar to SingleLiveEvent. It handles the events.
 *          SingleLiveEvent is a subclass of LiveData designed for handling the one time events in Android applications.
 *          e.g. setting errorMessage, setting Loaders etc.
 *          Similarly SharedFlow is also used to handle the one time event.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //        setContentView(R.layout.activity_main)
        //        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //            insets
        //        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainActivityViewModelFactory(125)
        viewModel = ViewModelProvider(this.viewModelFactory).get(MainActivityViewModel::class.java)

        // Android Live Data way
//        viewModel.totalData.observe(this, Observer {
//            binding.resultTextView.text = it.toString()
//        })

        // Kotlin StateFlow way , Consumer
        lifecycleScope.launchWhenCreated {
            viewModel.flowTotal.collect {
                binding.resultTextView.text = it.toString()
            }
        }

        binding.insertButton.setOnClickListener {
            viewModel.setTotal(binding.inputEditText.text.toString().toInt())
        }

        // Kotlin SharedFlow way , Consumer
        lifecycleScope.launch {
            viewModel.message.collect {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}