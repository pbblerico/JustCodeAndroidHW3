
package com.example.justcodetimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.justcodetimerapp.databinding.ActivityTimerBinding
import java.util.Locale

class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private lateinit var timer: CountDownTimer

    private var seconds: Long = 0
    private var currSeconds: Long = 0
    private var running: Boolean = false
    private var wasRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("tag", "on create")
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            seconds = it.getLong(ArgumentKey.SECONDS.name) * 1000
            currSeconds = seconds
            updateTextUI(currSeconds)
        }

        savedInstanceState?.let {bundle ->
            seconds = bundle.getLong(State.SECONDS.name)
            currSeconds = seconds
            running = bundle.getBoolean(State.RUNNING.name)
            wasRunning = bundle.getBoolean(State.WAS_RUNNING.name)
        }

        with(binding) {
            startBtn.setOnClickListener {
                startClick(currSeconds)
            }
            pauseBtn.setOnClickListener {
                pauseClick()
            }
            resetBtn.setOnClickListener {
                resetClick()
            }
        }
    }

    private fun updateTextUI(millsLeft: Long) {
        val minutes = ((millsLeft / 1000) % 3600) / 60
        val currSeconds = ((millsLeft / 1000) % 3600) % 60

        val time = String.format(Locale.getDefault(), "%02d:%02d", minutes, currSeconds)
        binding.timer.text = time
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(State.SECONDS.name, seconds)
        outState.putBoolean(State.RUNNING.name, running)
        outState.putBoolean(State.WAS_RUNNING.name, running)
        super.onSaveInstanceState(outState)
    }

    private fun startClick(startTime: Long) {
        running = true
        timer = object : CountDownTimer(startTime, 1000) {
            override fun onTick(millsLeft: Long) {
                updateTextUI(millsLeft)
                currSeconds = millsLeft
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun pauseClick() {
        running = false
        timer.cancel()
    }

    private fun resetClick() {
        running = false
        timer.cancel()
        currSeconds = seconds
        updateTextUI(currSeconds)
    }

    override fun onStart() {
        Log.d("tag", "on start")
        super.onStart()
    }

    override fun onResume() {
        Log.d("tag", "on resume")
        super.onResume()
        running = wasRunning
    }

    override fun onPause() {
        Log.d("tag", "on pause")
        super.onPause()
        running = false
    }

    override fun onStop() {
        Log.d("tag", "on stop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("tag", "on destroy")
        super.onDestroy()
    }
}