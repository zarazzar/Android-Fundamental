package com.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.dicoding.mybackgroundthread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //menggunakan executor and handler
//        val executor = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())

        binding.btnStart.setOnClickListener {
//            executor.execute {
//                try {
//                    //simulate process compressing
//                    for (i in 0..10) {
//                        Thread.sleep(500)
//                        val precentage = i * 10
//
//                        handler.post {
//
//                            if (precentage == 100) {
//                                binding.tvStatus.setText(R.string.task_completed)
//                            } else {
//                                binding.tvStatus.text =
//                                    String.format(getString(R.string.compressing), precentage)
//                            }
//                        }
//                    }

//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }

            lifecycleScope.launch(Dispatchers.Default) {
                //simulate process in background thread
                for (i in 0..10){
                    delay(500)
                    val precentage = i * 10
                    withContext(Dispatchers.Main) {
                        //update ui in main thread
                        if (precentage == 100 ){
                            binding.tvStatus.setText(R.string.task_completed)
                        } else {
                            binding.tvStatus.text= String.format(getString(R.string.compressing),precentage )
                        }
                    }

                }
            }
        }

    }
}
