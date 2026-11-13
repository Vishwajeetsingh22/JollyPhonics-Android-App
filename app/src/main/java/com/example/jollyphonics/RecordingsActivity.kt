package com.example.jollyphonics

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.File

class RecordingsActivity : AppCompatActivity() {

    private lateinit var listViewRecordings: ListView
    private lateinit var tvEmptyRecordings: TextView
    private val recordingList = mutableListOf<RecordingItem>()
    private lateinit var recordingAdapter: RecordingAdapter
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordings)

        listViewRecordings = findViewById(R.id.listViewRecordings)
        tvEmptyRecordings = findViewById(R.id.tvEmptyRecordings)

        loadRecordings()

        recordingAdapter = RecordingAdapter(
            this,
            recordingList,
            onPlayClick = { item -> playRecording(item) },
            onDeleteClick = { item -> deleteRecording(item) }
        )

        listViewRecordings.adapter = recordingAdapter
        updateEmptyView()
    }

    private fun loadRecordings() {
        recordingList.clear()
        val prefs = getSharedPreferences("JollyPhonicsPrefs", MODE_PRIVATE)
        val jsonString = prefs.getString("recordings_list", "[]")

        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val item = RecordingItem(
                    id = obj.getString("id"),
                    letter = obj.getString("letter"),
                    word = obj.getString("word"),
                    filePath = obj.getString("filePath"),
                    timestamp = obj.getString("timestamp")
                )
                // Verify file exists
                if (File(item.filePath).exists()) {
                    recordingList.add(item)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playRecording(item: RecordingItem) {
        releaseMediaPlayer()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(item.filePath)
                prepare()
                start()
                setOnCompletionListener {
                    releaseMediaPlayer()
                }
            }
            Toast.makeText(this, "Playing recording for Letter ${item.letter}...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error playing recording", Toast.LENGTH_SHORT).show()
            releaseMediaPlayer()
        }
    }

    private fun deleteRecording(item: RecordingItem) {
        // Delete physical file
        val file = File(item.filePath)
        if (file.exists()) {
            file.delete()
        }

        // Remove from list
        recordingList.remove(item)

        // Save updated list to SharedPreferences
        saveRecordingsToPrefs()

        recordingAdapter.notifyDataSetChanged()
        updateEmptyView()

        Toast.makeText(this, "Recording deleted", Toast.LENGTH_SHORT).show()
    }

    private fun saveRecordingsToPrefs() {
        val jsonArray = JSONArray()
        for (item in recordingList) {
            val obj = org.json.JSONObject().apply {
                put("id", item.id)
                put("letter", item.letter)
                put("word", item.word)
                put("filePath", item.filePath)
                put("timestamp", item.timestamp)
            }
            jsonArray.put(obj)
        }
        val prefs = getSharedPreferences("JollyPhonicsPrefs", MODE_PRIVATE)
        prefs.edit().putString("recordings_list", jsonArray.toString()).apply()
    }

    private fun updateEmptyView() {
        if (recordingList.isEmpty()) {
            listViewRecordings.visibility = View.GONE
            tvEmptyRecordings.visibility = View.VISIBLE
        } else {
            listViewRecordings.visibility = View.VISIBLE
            tvEmptyRecordings.visibility = View.GONE
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

    override fun onResume() {
        super.onResume()
        loadRecordings()
        if (::recordingAdapter.isInitialized) {
            recordingAdapter.notifyDataSetChanged()
        }
        updateEmptyView()
    }

    override fun onDestroy() {
        releaseMediaPlayer()
        super.onDestroy()
    }
}