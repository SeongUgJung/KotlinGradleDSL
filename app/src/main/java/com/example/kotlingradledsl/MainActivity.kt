package com.example.kotlingradledsl

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlingradledsl.library.Library

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper())
            .delayPost(1500) {
                findViewById<TextView>(R.id.text)
                    .text = Library().getString()
            }
    }

    private fun Handler.delayPost(delay: Long, runnable: Runnable) {
        postDelayed(runnable, delay)
    }
}