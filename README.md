# 🌿 Refugio

### A Calm and Wellbeing Support Android Application

> **Small tools for hard moments.**

Refugio is a student-developed Android application designed to provide a simple, calm space for users to check in with their mood and access small wellbeing activities.

The application combines mood journaling, calming exercises, quiet moments, daily challenges, and personal progress tracking in one simple Android application.

---

## ✨ Features

### 🔐 Account Management

* Create a local user account
* Login functionality
* Basic input validation
* User name is stored locally for personalized greetings

### 🌿 Calm

The Calm section provides simple activities that users can access when they want to take a short break.

It includes:

* **Breathing Exercises**
* **Grounding**
* **Quiet Moment**

### 🌬️ Breathing Exercises

Refugio provides different breathing activities, including:

* Box Breathing
* 4-7-8 Breathing
* Calm Breathing
* Custom Breathing

The breathing sessions provide a simple guided experience using on-screen instructions and timers.

### 🌊 Quiet Moment

The Quiet Moment feature provides a short 60-second guided pause.

It includes four sound options:

* Clock Ticking
* Waves
* Mountain Breeze
* Silent

The feature uses local audio files and a simple timer with a gentle visual animation.

### 📝 Mood Check-In

Users can record how they are feeling by selecting a mood and adding:

* Trigger
* Personal note
* Date

Mood entries are stored locally using SQLite.

### 📖 Mood History

Users can view their previously saved mood entries.

The history section allows users to:

* View saved entries
* Open an entry to see its details
* Delete an entry
* See an empty-state message when no entries exist

### ⭐ My Progress

The My Progress section displays simple personal progress information such as:

* Current streak
* XP
* Mood check-in count
* Most common mood
* Completed daily challenges
* Achievements

### ☀️ Daily Challenges

A small daily challenge is provided to encourage users to take a short break or perform a simple wellbeing activity.

Completing a challenge:

* Awards **10 XP**
* Increases the completed challenge count
* Prevents the same day's challenge from being completed again

---

## 🛠️ Technologies Used

| Technology          | Purpose                         |
| ------------------- | ------------------------------- |
| Java                | Application programming         |
| XML                 | User interface layouts          |
| Android Studio      | Development environment         |
| Android SDK         | Android application development |
| SQLite              | Local storage                   |
| SQLiteOpenHelper    | Database management             |
| SharedPreferences   | Local user and progress data    |
| Material Components | UI components                   |
| MediaPlayer         | Quiet Moment audio              |
| CountDownTimer      | Timed activities                |
| Intents             | Activity navigation             |

---

## 🗄️ Data Storage

Refugio uses local storage instead of a cloud backend.

### SQLite

SQLite is used to store:

* User account information
* Mood entries
* Mood
* Trigger
* Notes
* Date

### SharedPreferences

SharedPreferences is used for lightweight application data such as:

* Saved user name
* Saved email
* XP
* Daily challenge completion
* Challenge count

This allows the core application to work without requiring an internet connection or external server.

---

## 📱 Application Flow

```text
Create Account / Login
        ↓
     Welcome
        ↓
    Dashboard
        ↓
 ┌──────┼──────────┬───────────┐
 ↓      ↓          ↓           ↓
Calm  Check-In   History    My Progress
 ↓
 ├── Breathing
 ├── Grounding
 └── Quiet Moment
                 ↓
          Daily Challenge
```

---

## 🎨 Design

Refugio follows a calm, nature-inspired visual style.

The interface uses:

* Warm off-white backgrounds
* Muted teal and blue tones
* Rounded cards
* Simple navigation
* Soft visual elements
* Minimal and readable layouts

The design was intentionally kept mature and simple rather than using a childish or heavily animated style.

---

## 📂 Project Structure

```text
Refugio
│
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── com.example.refugio
│           │       ├── MainActivity.java
│           │       ├── LoginActivity.java
│           │       ├── WelcomeActivity.java
│           │       ├── DashBoardActivity.java
│           │       ├── CalmActivity.java
│           │       ├── BreathActivity.java
│           │       ├── BreathingSessionActivity.java
│           │       ├── CalmBreathingActivity.java
│           │       ├── CustomBreathingActivity.java
│           │       ├── FourSevenEightActivity.java
│           │       ├── GroundingActivity.java
│           │       ├── QuietMomentActivity.java
│           │       ├── MoodLogActivity.java
│           │       ├── MoodEntryActivity.java
│           │       ├── MoodHistoryActivity.java
│           │       ├── DailyChallengeActivity.java
│           │       ├── RoutineActivity.java
│           │       └── MoodDatabaseHelper.java
│           │
│           └── res
│               ├── drawable
│               ├── layout
│               ├── raw
│               ├── values
│               └── anim
│
├── gradle
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🚀 How to Run

### Requirements

* Android Studio
* Android SDK
* Java
* Android phone or compatible Android device

### Steps

1. Clone this repository.
2. Open the project in Android Studio.
3. Allow Gradle synchronization to complete.
4. Connect an Android phone with USB debugging enabled or use a compatible Android device.
5. Run the application from Android Studio.

---

## 🔒 Privacy and Scope

Refugio is designed as a student-level wellbeing application.

The application stores its core data locally on the device and does not require a cloud backend.

Refugio is intended for general wellbeing and self-care support. It is **not a medical diagnostic or treatment system** and should not be treated as a replacement for professional care.

---

## 🔮 Future Scope

Possible future improvements include:

* Optional photo attachments for mood entries
* More detailed mood analytics
* Reminders for mood check-ins
* Cloud synchronization
* Firebase integration
* More personalized routines
* Additional accessibility improvements
* More breathing and grounding activities
* Improved progress visualization

These features are not part of the current version.

---

## 🎓 Academic Project

**Project:** Refugio
**Type:** Android / Mobile Application Development Project
**Platform:** Android
**Language:** Java
**UI:** XML
**Database:** SQLite

Developed as a student Mobile Application Development project.

---

## 📌 Current Version

**Version:** 1.0

The current version focuses on the core Refugio experience:

**Calm • Check-In • Reflect • Progress**

---

## 📄 License

This project was developed as an academic/student project.
