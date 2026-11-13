package com.example.jollyphonics

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PhonicsActivity : AppCompatActivity() {

    private lateinit var tvPhonicsLetter: TextView
    private lateinit var tvPhonicsSound: TextView
    private lateinit var tvPhonicsWord: TextView
    private lateinit var tvPhonicsDescription: TextView
    private lateinit var tvRecordStatus: TextView
    private lateinit var btnPlayPronunciation: Button
    private lateinit var btnStartRecord: Button
    private lateinit var btnStopRecord: Button

    private var letter: String = "A"
    private var sound: String = "/a/"
    private var word: String = "Apple"
    private var description: String = ""

    private var pronunciationMediaPlayer: MediaPlayer? = null
    private var mediaRecorder: MediaRecorder? = null
    private var audioFilePath: String = ""
    private var isRecording = false

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phonics)

        letter = intent.getStringExtra("LETTER") ?: "A"
        sound = intent.getStringExtra("SOUND") ?: "/a/"
        word = intent.getStringExtra("WORD") ?: "Apple"
        description = intent.getStringExtra("DESCRIPTION") ?: ""

        tvPhonicsLetter = findViewById(R.id.tvPhonicsLetter)
        tvPhonicsSound = findViewById(R.id.tvPhonicsSound)
        tvPhonicsWord = findViewById(R.id.tvPhonicsWord)
        tvPhonicsDescription = findViewById(R.id.tvPhonicsDescription)
        tvRecordStatus = findViewById(R.id.tvRecordStatus)
        btnPlayPronunciation = findViewById(R.id.btnPlayPronunciation)
        btnStartRecord = findViewById(R.id.btnStartRecord)
        btnStopRecord = findViewById(R.id.btnStopRecord)

        tvPhonicsLetter.text = letter
        tvPhonicsSound.text = "Sound: $sound"
        tvPhonicsWord.text = "Example: $word"
        tvPhonicsDescription.text = description

        btnPlayPronunciation.setOnClickListener {
            playPronunciation()
        }

        btnStartRecord.setOnClickListener {
            checkPermissionAndStartRecording()
        }

        btnStopRecord.setOnClickListener {
            stopRecording()
        }
    }

    private fun playPronunciation() {
        releasePronunciationMediaPlayer()
        val resName = "sound_${letter.lowercase(Locale.getDefault())}"
        val resId = resources.getIdentifier(resName, "raw", packageName)
        if (resId != 0) {
            pronunciationMediaPlayer = MediaPlayer.create(this, resId).apply {
                start()
                setOnCompletionListener {
                    releasePronunciationMediaPlayer()
                }
            }
            Toast.makeText(this, "Playing Jolly Phonics sound for $letter", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Audio sound not found for $letter", Toast.LENGTH_SHORT).show()
        }
    }

    private fun releasePronunciationMediaPlayer() {
        pronunciationMediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        pronunciationMediaPlayer = null
    }

    private fun checkPermissionAndStartRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        } else {
            startRecording()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording()
            } else {
                Toast.makeText(this, "Microphone permission is required to record voice.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRecording() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "rec_${letter}_${timeStamp}.3gp"
        val file = File(filesDir, fileName)
        audioFilePath = file.absolutePath

        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFilePath)
            try {
                prepare()
                start()
                isRecording = true
                tvRecordStatus.text = getString(R.string.status_recording)
                btnStartRecord.isEnabled = false
                btnStopRecord.isEnabled = true
                Toast.makeText(this@PhonicsActivity, "Recording started...", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@PhonicsActivity, "Recording failed to start", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            try {
                mediaRecorder?.apply {
                    stop()
                    release()
                }
                mediaRecorder = null
                isRecording = false
                tvRecordStatus.text = getString(R.string.status_recorded)
                btnStartRecord.isEnabled = true
                btnStopRecord.isEnabled = false

                saveRecordingMetadata(fileNameFromPath(audioFilePath), audioFilePath)
                Toast.makeText(this, "Recording saved successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error stopping recording", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fileNameFromPath(path: String): String {
        return File(path).name
    }

    private fun saveRecordingMetadata(fileName: String, filePath: String) {
        val prefs = getSharedPreferences("JollyPhonicsPrefs", MODE_PRIVATE)
        val jsonString = prefs.getString("recordings_list", "[]")
        val jsonArray = JSONArray(jsonString)

        val timeString = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())

        val obj = JSONObject().apply {
            put("id", fileName)
            put("letter", letter)
            put("word", word)
            put("filePath", filePath)
            put("timestamp", timeString)
        }

        jsonArray.put(obj)
        prefs.edit().putString("recordings_list", jsonArray.toString()).apply()
    }

    override fun onDestroy() {
        releasePronunciationMediaPlayer()
        if (isRecording) {
            try {
                mediaRecorder?.stop()
                mediaRecorder?.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onDestroy()
    }
}