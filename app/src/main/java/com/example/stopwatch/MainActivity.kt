package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private lateinit var buttonStart: MaterialButton
    private lateinit var buttonPause: MaterialButton
    private lateinit var buttonReset: MaterialButton
    private val handler = Handler()
    private var startTime = 0L
    private var isRunning = false

    private val updateTimerThread = object : Runnable {
        override fun run() {
            val millis = System.currentTimeMillis() - startTime
            var seconds = (millis / 1000).toInt()
            var minutes = seconds / 60
            val hours = minutes / 60
            seconds %= 60
            minutes %= 60
            textViewTimer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)
        buttonStart = findViewById(R.id.buttonStart)
        buttonPause = findViewById(R.id.buttonPause)
        buttonReset = findViewById(R.id.buttonReset)

        buttonStart.setOnClickListener {
            if (!isRunning) {
                startTime = System.currentTimeMillis()
                handler.postDelayed(updateTimerThread, 0)
                isRunning = true
            }
        }

        buttonPause.setOnClickListener {
            if (isRunning) {
                handler.removeCallbacks(updateTimerThread)
                isRunning = false
            }
        }

        buttonReset.setOnClickListener {
            handler.removeCallbacks(updateTimerThread)
            isRunning = false
            textViewTimer.text = "00:00:00"
        }
    }
}
