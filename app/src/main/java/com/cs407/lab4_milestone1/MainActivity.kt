package com.cs407.lab4_milestone1

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import kotlinx.coroutines.isActive

class MainActivity : AppCompatActivity() {
    private val TAG = "MyActivity"
    private var job : Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Call startDownload() when "Start" button is clicked
        val startButton = findViewById<Button>(R.id.start)
        startButton.setOnClickListener { view ->
            startDownload(view)
        }

        val stopButton = findViewById<Button>(R.id.stop)
        stopButton.setOnClickListener { view ->
            stopDownload(view)
        }
    }

    private suspend fun mockFileDownloader() {

        withContext(Dispatchers.Main) {
            val progressText = findViewById<TextView>(R.id.progressText)
            progressText.visibility = View.VISIBLE

            val startButton = findViewById<Button>(R.id.start)
            startButton.text = getString(R.string.download)
        }

        for (downloadProgress in 0..100 step 10) {

            Log.d(TAG, "Download Progress $downloadProgress%")

            withContext(Dispatchers.Main) {
                val progressText = findViewById<TextView>(R.id.progressText)
                progressText.text = "Download Progress: $downloadProgress%"
            }

            delay(1000)
        }

        withContext(Dispatchers.Main) {
            val startButton = findViewById<Button>(R.id.start)
            startButton.text = "START"

            val progressText = findViewById<TextView>(R.id.progressText)
            progressText.text = "Download Completed"
        }
    }
        fun startDownload(view: View) {
            // Start the coroutine for downloading
            job = CoroutineScope(Dispatchers.Default).launch {
                mockFileDownloader()
            }
        }
        fun stopDownload(view: View) {
            Log.d(TAG, "Stopping download...")

            val progressText = findViewById<TextView>(R.id.progressText)
            progressText.text = "Download Cancelled"
            val startButton = findViewById<Button>(R.id.start)
            startButton.text = "START"

            job?.cancel()
        }
    }
