# 🎵 JollyPhonics – Phonics Learning Android Application

An interactive Android application designed to help children learn the phonics sounds of all 26 English alphabets (A–Z) through audio pronunciation, example words, and voice recording practice.

## 📌 Project Information

| Detail | Description |
|---|---|
| Project Name | JollyPhonics |
| Platform | Android |
| Language | Kotlin |
| UI Design | XML |
| IDE | Android Studio |
| Subject | Mobile Application Development (MAD) |
| University | Jain (Deemed-to-be University) |
| Developer | Vishwajeet Singh |

## 🎯 Objective

The main objective of JollyPhonics is to make English phonics learning simple, interactive, and enjoyable for children. It provides alphabet recognition, phonics pronunciation, audio playback, and voice recording facilities to improve pronunciation skills.

## ✨ Features

- **Alphabet Display (A–Z):** Displays all 26 English alphabets in a GridView.
- **Phonics Pronunciation:** Shows the corresponding phonics sound and example word for each letter.
- **Audio Playback:** Plays pronunciation audio using MediaPlayer.
- **Voice Recording:** Allows users to record their own pronunciation using MediaRecorder.
- **Recording Management:** Saves recorded audio files locally.
- **Recording List:** Displays saved recordings using ListView.
- **Playback and Review:** Allows users to listen to their recorded pronunciation.
- **User-Friendly Interface:** Simple and colorful design suitable for children.

## 🛠️ Technologies Used

- Kotlin
- XML
- Android Studio
- Android SDK
- GridView
- MediaPlayer
- MediaRecorder
- ListView
- Intent
- Runtime Permissions
- Internal Storage

## 📱 Application Modules

### 1. Home Screen
Provides navigation to the alphabet learning section and saved recordings.

### 2. Alphabet Grid
Displays all 26 English letters (A–Z) in a GridView.

### 3. Phonics Learning
Displays the selected letter, its phonics sound, and an example word with an audio playback option.

### 4. Voice Recording
Allows users to record and save their pronunciation using the device microphone.

### 5. My Recordings
Displays saved audio recordings with options to play and review them.

## 🔤 Alphabet Examples

| Letter | Phonics Sound | Example Word |
|---|---|---|
| A | /æ/ | Apple |
| B | /b/ | Ball |
| C | /k/ | Cat |
| D | /d/ | Dog |
| E | /e/ | Elephant |
| F | /f/ | Fish |
| G | /g/ | Goat |
| H | /h/ | Hat |
| I | /ɪ/ | Ink |
| J | /dʒ/ | Jug |
| K | /k/ | Kite |
| L | /l/ | Lion |
| M | /m/ | Mango |
| N | /n/ | Nest |
| O | /ɒ/ | Orange |
| P | /p/ | Parrot |
| Q | /kw/ | Queen |
| R | /r/ | Rabbit |
| S | /s/ | Sun |
| T | /t/ | Tiger |
| U | /ʌ/ | Umbrella |
| V | /v/ | Van |
| W | /w/ | Watch |
| X | /ks/ | X-ray |
| Y | /j/ | Yak |
| Z | /z/ | Zebra |

## 📂 Project Structure

```text
JollyPhonics/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── your/package/name/
│           │       ├── MainActivity.kt
│           │       ├── AlphabetActivity.kt
│           │       ├── PhonicsActivity.kt
│           │       ├── RecordingsActivity.kt
│           │       ├── AlphabetAdapter.kt
│           │       └── RecordingAdapter.kt
│           │
│           ├── res/
│           │   ├── layout/
│           │   ├── drawable/
│           │   ├── mipmap/
│           │   ├── values/
│           │   └── raw/
│           │
│           └── AndroidManifest.xml
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

*Note: The structure above represents the intended organization. Actual filenames may differ depending on the implementation.*

## ⚙️ Installation and Execution

1. Clone the repository:

```bash
git clone https://github.com/Vishwajeetsingh22/JollyPhonics-Android-App.git
```

2. Open Android Studio.

3. Select **Open** and choose the cloned JollyPhonics project folder.

4. Allow Gradle synchronization to complete.

5. Connect an Android device or start an Android emulator.

6. Click the **Run ▶** button to build and install the application.

7. Grant microphone permission when prompted to use voice recording.

## 🔐 Permissions

The application requires microphone permission for voice recording.

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

Runtime permission should also be requested before starting a recording.

## 🎓 MAD Concepts Demonstrated

| Concept | Implementation |
|---|---|
| GridView | Displays A–Z alphabets |
| MediaPlayer | Plays phonics audio |
| MediaRecorder | Records pronunciation |
| ListView | Displays saved recordings |
| Intent | Navigates between activities |
| Runtime Permission | Accesses the microphone |

## 🧪 Expected Output

The application demonstrates:

1. Home screen with learning options.
2. Alphabet GridView containing A–Z.
3. Phonics sounds with example words.
4. Audio pronunciation playback.
5. Voice recording and saving.
6. ListView containing saved recordings.

## 👨‍💻 Developer

**Vishwajeet Singh**  
MCA Student  
Jain (Deemed-to-be University)
25MCAR0219

- GitHub: [@Vishwajeetsingh22](https://github.com/Vishwajeetsingh22)

## 🔗 Repository

[JollyPhonics – Android Application](https://github.com/Vishwajeetsingh22/JollyPhonics-Android-App)

## 📄 License

This project is developed for educational purposes as part of the Mobile Application Development (MAD) Lab.

---

⭐ If you find this project useful, feel free to explore the repository!
