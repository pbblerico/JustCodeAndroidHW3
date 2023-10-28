package com.example.justcodetimerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.justcodetimerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startClick()
        sendClick()
    }

    private fun startClick() {
        binding.startButton.setOnClickListener{
            Log.d("Log", "jello")
            if(isValid()) {
                val intent = Intent(this, TimerActivity::class.java)
                intent.putExtra(ArgumentKey.SECONDS.name, binding.input.text.toString().toLong())
                startActivity(intent)
            } else {
                Toast.makeText(this, "You have no input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendClick() {
        binding.sendButton.setOnClickListener{

        }
    }

    private fun isValid() = !binding.input.text.isNullOrBlank()
}