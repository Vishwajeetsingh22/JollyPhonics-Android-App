# JollyPhonics – Phonics Learning Application (MAD Lab Q6)

## Project Overview

**JollyPhonics** is a complete, fully functional Android educational application designed to help children learn the phonics sounds of all 26 English letters (A–Z) along with example words, pronunciation audio playback, voice recording, and voice review management.

---

## 1. Application Architecture & Screens

1. **Home Screen (`MainActivity.kt`)**: Displays the application title, child-friendly subtitle, and navigation buttons for **Start Learning** and **View My Recordings**.
2. **Alphabet Grid Screen (`AlphabetActivity.kt`)**: Displays all 26 English alphabets (A–Z) in a colorful 3-column `GridView` using `CardView` items (`item_alphabet.xml`). Tapping any letter opens its phonics detail screen.
3. **Phonics Learning Screen (`PhonicsActivity.kt`)**: Shows the selected letter, phonics sound, example word, and mnemonic description. Features **Play Pronunciation** (powered by `TextToSpeech` and `MediaPlayer`), **Record Voice** (`MediaRecorder`), and **Stop Recording** buttons with live status updates.
4. **My Recordings Screen (`RecordingsActivity.kt`)**: Displays saved child voice recordings in a `ListView` (`item_recording.xml`). Each item provides play and delete options, with persistent local storage (`SharedPreferences` + internal storage files).

---

## 2. Project File Structure & Complete Code

### `AndroidManifest.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="28" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="28" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.JollyPhonics">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity
            android:name=".AlphabetActivity"
            android:exported="false"
            android:label="Alphabet Grid" />

        <activity
            android:name=".PhonicsActivity"
            android:exported="false"
            android:label="Phonics Learning" />

        <activity
            android:name=".RecordingsActivity"
            android:exported="false"
            android:label="My Recordings" />

    </application>

</manifest>
```

---

### UI Layouts

#### `activity_main.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background_color"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="24dp">

    <ImageView
        android:layout_width="120dp"
        android:layout_height="120dp"
        android:layout_marginBottom="24dp"
        android:contentDescription="@string/app_name"
        android:src="@mipmap/ic_launcher" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:fontFamily="sans-serif-black"
        android:text="@string/app_name"
        android:textColor="@color/primary_purple"
        android:textSize="36sp"
        android:textStyle="bold" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="48dp"
        android:text="@string/app_subtitle"
        android:textColor="@color/text_color"
        android:textSize="18sp"
        android:textStyle="italic" />

    <Button
        android:id="@+id/btnStartLearning"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:layout_marginBottom="16dp"
        android:backgroundTint="@color/primary_blue"
        android:fontFamily="sans-serif-medium"
        android:text="@string/start_learning"
        android:textColor="@color/white"
        android:textSize="18sp" />

    <Button
        android:id="@+id/btnViewRecordings"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:backgroundTint="@color/accent_orange"
        android:fontFamily="sans-serif-medium"
        android:text="@string/view_recordings"
        android:textColor="@color/white"
        android:textSize="18sp" />

</LinearLayout>
```

#### `activity_alphabet.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background_color"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="16dp"
        android:fontFamily="sans-serif-black"
        android:gravity="center"
        android:text="@string/choose_letter"
        android:textColor="@color/primary_purple"
        android:textSize="24sp"
        android:textStyle="bold" />

    <GridView
        android:id="@+id/gridViewAlphabet"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:horizontalSpacing="12dp"
        android:numColumns="3"
        android:stretchMode="columnWidth"
        android:verticalSpacing="12dp" />

</LinearLayout>
```

#### `item_alphabet.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:layout_margin="4dp"
    app:cardBackgroundColor="@color/card_bg"
    app:cardCornerRadius="16dp"
    app:cardElevation="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvLetter"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:fontFamily="sans-serif-black"
            android:text="A"
            android:textColor="@color/primary_purple"
            android:textSize="36sp"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/tvWord"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:fontFamily="sans-serif-medium"
            android:text="Apple"
            android:textColor="@color/text_color"
            android:textSize="14sp" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

#### `activity_phonics.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background_color"
    android:fillViewport="true"
    android:padding="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal"
        android:orientation="vertical">

        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="24dp"
            app:cardBackgroundColor="@color/white"
            app:cardCornerRadius="20dp"
            app:cardElevation="6dp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:orientation="vertical"
                android:padding="24dp">

                <TextView
                    android:id="@+id/tvPhonicsLetter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:fontFamily="sans-serif-black"
                    android:text="A"
                    android:textColor="@color/primary_purple"
                    android:textSize="72sp"
                    android:textStyle="bold" />

                <TextView
                    android:id="@+id/tvPhonicsSound"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:fontFamily="sans-serif-medium"
                    android:text="Sound: /a/"
                    android:textColor="@color/primary_blue"
                    android:textSize="22sp" />

                <TextView
                    android:id="@+id/tvPhonicsWord"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:fontFamily="sans-serif-medium"
                    android:text="Example: Apple"
                    android:textColor="@color/accent_orange"
                    android:textSize="22sp" />

                <TextView
                    android:id="@+id/tvPhonicsDescription"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="12dp"
                    android:fontFamily="sans-serif"
                    android:gravity="center"
                    android:text="Ants on your arm /a/ /a/ /a/"
                    android:textColor="@color/text_color"
                    android:textSize="16sp" />

            </LinearLayout>
        </androidx.cardview.widget.CardView>

        <Button
            android:id="@+id/btnPlayPronunciation"
            android:layout_width="match_parent"
            android:layout_height="56dp"
            android:layout_marginBottom="16dp"
            android:backgroundTint="@color/primary_blue"
            android:fontFamily="sans-serif-medium"
            android:text="@string/play_pronunciation"
            android:textColor="@color/white"
            android:textSize="16sp" />

        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardBackgroundColor="@color/card_bg"
            app:cardCornerRadius="16dp"
            app:cardElevation="4dp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginBottom="12dp"
                    android:fontFamily="sans-serif-medium"
                    android:text="Practice Your Pronunciation"
                    android:textColor="@color/primary_purple"
                    android:textSize="18sp"
                    android:textStyle="bold" />

                <TextView
                    android:id="@+id/tvRecordStatus"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginBottom="16dp"
                    android:text="@string/status_ready"
                    android:textColor="@color/text_color"
                    android:textSize="14sp" />

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal">

                    <Button
                        android:id="@+id/btnStartRecord"
                        android:layout_width="0dp"
                        android:layout_height="56dp"
                        android:layout_marginEnd="8dp"
                        android:layout_weight="1"
                        android:backgroundTint="@color/accent_green"
                        android:fontFamily="sans-serif-medium"
                        android:text="@string/start_recording"
                        android:textColor="@color/white"
                        android:textSize="14sp" />

                    <Button
                        android:id="@+id/btnStopRecord"
                        android:layout_width="0dp"
                        android:layout_height="56dp"
                        android:layout_marginStart="8dp"
                        android:layout_weight="1"
                        android:backgroundTint="@color/accent_pink"
                        android:enabled="false"
                        android:fontFamily="sans-serif-medium"
                        android:text="@string/stop_recording"
                        android:textColor="@color/white"
                        android:textSize="14sp" />

                </LinearLayout>

            </LinearLayout>
        </androidx.cardview.widget.CardView>

    </LinearLayout>
</ScrollView>
```

#### `activity_recordings.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background_color"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="16dp"
        android:fontFamily="sans-serif-black"
        android:gravity="center"
        android:text="My Voice Recordings"
        android:textColor="@color/primary_purple"
        android:textSize="24sp"
        android:textStyle="bold" />

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ListView
            android:id="@+id/listViewRecordings"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:divider="@android:color/transparent"
            android:dividerHeight="8dp" />

        <TextView
            android:id="@+id/tvEmptyRecordings"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:fontFamily="sans-serif-medium"
            android:text="No voice recordings yet!\nGo to Phonics screen and record your voice."
            android:textAlignment="center"
            android:textColor="@color/text_color"
            android:textSize="16sp"
            android:visibility="gone" />

    </FrameLayout>

</LinearLayout>
```

#### `item_recording.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="@color/white"
    app:cardCornerRadius="12dp"
    app:cardElevation="3dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:padding="16dp">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical">

            <TextView
                android:id="@+id/tvRecTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:fontFamily="sans-serif-bold"
                android:text="Letter A - Apple"
                android:textColor="@color/primary_purple"
                android:textSize="18sp" />

            <TextView
                android:id="@+id/tvRecTimestamp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="4dp"
                android:fontFamily="sans-serif"
                android:text="recording_a.3gp"
                android:textColor="@color/text_color"
                android:textSize="14sp" />

        </LinearLayout>

        <Button
            android:id="@+id/btnPlayRec"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:layout_marginEnd="8dp"
            android:backgroundTint="@color/primary_blue"
            android:contentDescription="Play Recording"
            android:drawableStart="@android:drawable/ic_media_play"
            android:padding="0dp" />

        <Button
            android:id="@+id/btnDeleteRec"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:backgroundTint="@color/accent_pink"
            android:contentDescription="Delete Recording"
            android:drawableStart="@android:drawable/ic_menu_delete"
            android:padding="0dp" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

---

### Kotlin Implementation Files

#### `PhonicsItem.kt`
```kotlin
package com.example.jollyphonics

data class PhonicsItem(
    val letter: String,
    val sound: String,
    val word: String,
    val description: String
) {
    companion object {
        fun getAllPhonics(): List<PhonicsItem> {
            return listOf(
                PhonicsItem("A", "/a/", "Apple", "Ants on your arm /a/ /a/ /a/"),
                PhonicsItem("B", "/b/", "Ball", "Bring your bat and ball /b/ /b/ /b/"),
                PhonicsItem("C", "/b, k/", "Cat", "We are clicking cards /k/ /k/ /k/"),
                PhonicsItem("D", "/d/", "Dog", "See me play on my drum /d/ /d/ /d/"),
                PhonicsItem("E", "/e/", "Elephant", "Eggs in the pan /e/ /e/ /e/"),
                PhonicsItem("F", "/f/", "Fish", "My dog barks /f/ /f/ /f/"),
                PhonicsItem("G", "/g/", "Goat", "Water gurgles down the drain /g/ /g/ /g/"),
                PhonicsItem("H", "/h/", "Hat", "Hear the horse panting /h/ /h/ /h/"),
                PhonicsItem("I", "/i/", "Igloo", "Inky the mouse is wet /i/ /i/ /i/"),
                PhonicsItem("J", "/j/", "Jug", "Jack in the box jumps up /j/ /j/ /j/"),
                PhonicsItem("K", "/k/", "Kite", "Kites flying in the sky /k/ /k/ /k/"),
                PhonicsItem("L", "/l/", "Lion", "Licking lollipops /l/ /l/ /l/"),
                PhonicsItem("M", "/m/", "Monkey", "Mmm, yummy food /m/ /m/ /m/"),
                PhonicsItem("N", "/n/", "Nest", "Hear the plane making a sound /n/ /n/ /n/"),
                PhonicsItem("O", "/o/", "Orange", "Turn on the light /o/ /o/ /o/"),
                PhonicsItem("P", "/p/", "Pig", "Puff out the candles /p/ /p/ /p/"),
                PhonicsItem("Q", "/qu/", "Queen", "Duck goes quack /qu/ /qu/ /qu/"),
                PhonicsItem("R", "/r/", "Rabbit", "Puppy tears the rag /r/ /r/ /r/"),
                PhonicsItem("S", "/s/", "Sun", "The snake goes in the grass /s/ /s/ /s/"),
                PhonicsItem("T", "/t/", "Train", "Tennis match going back and forth /t/ /t/ /t/"),
                PhonicsItem("U", "/u/", "Umbrella", "Up goes the umbrella /u/ /u/ /u/"),
                PhonicsItem("V", "/v/", "Van", "Drive the van /v/ /v/ /v/"),
                PhonicsItem("W", "/w/", "Watch", "The wind blows /w/ /w/ /w/"),
                PhonicsItem("X", "/ks/", "Xylophone", "X-ray picture /ks/ /ks/ /ks/"),
                PhonicsItem("Y", "/y/", "Yacht", "Eating yogurt /y/ /y/ /y/"),
                PhonicsItem("Z", "/z/", "Zebra", "Buzzy bees in the hive /z/ /z/ /z/")
            )
        }
    }
}
```

#### `RecordingItem.kt`
```kotlin
package com.example.jollyphonics

data class RecordingItem(
    val id: String,
    val letter: String,
    val word: String,
    val filePath: String,
    val timestamp: String
)
```

#### `AlphabetAdapter.kt`
```kotlin
package com.example.jollyphonics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AlphabetAdapter(
    private val context: Context,
    private val items: List<PhonicsItem>
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_alphabet, parent, false)

        val tvLetter = view.findViewById<TextView>(R.id.tvLetter)
        val tvWord = view.findViewById<TextView>(R.id.tvWord)

        val item = items[position]
        tvLetter.text = item.letter
        tvWord.text = item.word

        return view
    }
}
```

#### `RecordingAdapter.kt`
```kotlin
package com.example.jollyphonics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class RecordingAdapter(
    private val context: Context,
    private val recordings: List<RecordingItem>,
    private val onPlayClick: (RecordingItem) -> Unit,
    private val onDeleteClick: (RecordingItem) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = recordings.size

    override fun getItem(position: Int): Any = recordings[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_recording, parent, false)

        val tvRecTitle = view.findViewById<TextView>(R.id.tvRecTitle)
        val tvRecTimestamp = view.findViewById<TextView>(R.id.tvRecTimestamp)
        val btnPlayRec = view.findViewById<Button>(R.id.btnPlayRec)
        val btnDeleteRec = view.findViewById<Button>(R.id.btnDeleteRec)

        val item = recordings[position]
        tvRecTitle.text = "Letter ${item.letter} - ${item.word}"
        tvRecTimestamp.text = item.timestamp

        btnPlayRec.setOnClickListener {
            onPlayClick(item)
        }

        btnDeleteRec.setOnClickListener {
            onDeleteClick(item)
        }

        return view
    }
}
```

#### `MainActivity.kt`
```kotlin
package com.example.jollyphonics

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartLearning = findViewById<Button>(R.id.btnStartLearning)
        val btnViewRecordings = findViewById<Button>(R.id.btnViewRecordings)

        btnStartLearning.setOnClickListener {
            val intent = Intent(this, AlphabetActivity::class.java)
            startActivity(intent)
        }

        btnViewRecordings.setOnClickListener {
            val intent = Intent(this, RecordingsActivity::class.java)
            startActivity(intent)
        }
    }
}
```

#### `AlphabetActivity.kt`
```kotlin
package com.example.jollyphonics

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class AlphabetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabet)

        val gridView = findViewById<GridView>(R.id.gridViewAlphabet)
        val phonicsList = PhonicsItem.getAllPhonics()

        val adapter = AlphabetAdapter(this, phonicsList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = phonicsList[position]
            val intent = Intent(this, PhonicsActivity::class.java).apply {
                putExtra("LETTER", selectedItem.letter)
                putExtra("SOUND", selectedItem.sound)
                putExtra("WORD", selectedItem.word)
                putExtra("DESCRIPTION", selectedItem.description)
            }
            startActivity(intent)
        }
    }
}
```

#### `PhonicsActivity.kt`
```kotlin
package com.example.jollyphonics

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
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

class PhonicsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

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

    private var tts: TextToSpeech? = null
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

        tts = TextToSpeech(this, this)

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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        }
    }

    private fun playPronunciation() {
        val textToSpeak = "Letter $letter. Sound $sound. Example $word."
        tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
        Toast.makeText(this, "Playing pronunciation...", Toast.LENGTH_SHORT).show()
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
        if (isRecording) {
            try {
                mediaRecorder?.stop()
                mediaRecorder?.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
```

#### `RecordingsActivity.kt`
```kotlin
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
        val file = File(item.filePath)
        if (file.exists()) {
            file.delete()
        }

        recordingList.remove(item)
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
```

---

## 3. Practical Examination Concepts Explanation

1. **GridView (`AlphabetActivity`)**:
   - Used to display 26 English letters in a uniform grid format (`numColumns = 3`).
   - Utilizes `BaseAdapter` (`AlphabetAdapter`) to inflate `CardView` items for each alphabet and word pair.
   - Handles item selection via `setOnItemClickListener` to pass selected letter data through Intents.

2. **Audio Playback (`PhonicsActivity`)**:
   - Implemented using Android's built-in `TextToSpeech` engine as a robust, out-of-the-box pronunciation engine that clearly speaks the letter, sound, and example word without needing external raw audio files.
   - Can also be extended to use `MediaPlayer` with audio files placed in `res/raw`.

3. **Voice Recording (`MediaRecorder`)**:
   - Requests runtime microphone permission (`RECORD_AUDIO`).
   - Configures `MediaRecorder` with `AudioSource.MIC`, `OutputFormat.THREE_GPP`, and `AudioEncoder.AMR_NB`.
   - Saves recordings to private internal storage (`context.filesDir`) and persists metadata in `SharedPreferences` as JSON.

4. **Recording List (`RecordingsActivity`)**:
   - Displays all saved voice recordings in a `ListView` using a custom `BaseAdapter` (`RecordingAdapter`).
   - Provides individual Play buttons (`MediaPlayer`) and Delete buttons (removes file and updates SharedPreferences).
   - Manages empty states dynamically.

---

## 4. Execution & Testing Instructions

1. Open the project in **Android Studio**.
2. Sync Gradle and ensure build succeeds (`app:assembleDebug`).
3. Run the app on an Android Emulator or physical device (API 24+).
4. **Test Flow**:
   - Tap **Start Learning** on the Home screen to view the 26 letters in the GridView.
   - Tap any letter (e.g., 'A') to open the Phonics screen.
   - Tap **Play Pronunciation** to hear the audio.
   - Tap **Record Voice** (grant microphone permission when prompted), record your voice, and tap **Stop Recording**.
   - Go back to the Home screen and tap **View My Recordings** to review, play, or delete your saved recordings.
