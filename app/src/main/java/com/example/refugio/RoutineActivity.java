package com.example.refugio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RoutineActivity extends AppCompatActivity {

    private TextView streakText;
    private TextView xpText;
    private TextView nextXpText;
    private ProgressBar xpProgress;

    private TextView checkInsText;
    private TextView commonMoodText;
    private TextView challengesText;
    private TextView achievementText;

    private MoodDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        databaseHelper =
                new MoodDatabaseHelper(this);

        ImageView backButton =
                findViewById(R.id.backButton);

        MaterialButton challengeButton =
                findViewById(R.id.challengeButton);

        streakText =
                findViewById(R.id.streakText);

        xpText =
                findViewById(R.id.xpText);

        nextXpText =
                findViewById(R.id.nextXpText);

        xpProgress =
                findViewById(R.id.xpProgress);

        checkInsText =
                findViewById(R.id.checkInsText);

        commonMoodText =
                findViewById(R.id.commonMoodText);

        challengesText =
                findViewById(R.id.challengesText);

        achievementText =
                findViewById(R.id.achievementText);

        backButton.setOnClickListener(v -> finish());

        challengeButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RoutineActivity.this,
                            DailyChallengeActivity.class
                    );

            startActivity(intent);
        });

        loadProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProgress();
    }

    private void loadProgress() {

        loadXP();
        loadMoodStats();
        loadChallenges();
        loadAchievements();
        loadStreak();
    }

    private void loadXP() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                );

        int xp =
                preferences.getInt(
                        "xp",
                        0
                );

        xpText.setText(
                "XP: " + xp
        );

        int nextLevelXP = 100;

        nextXpText.setText(
                xp + " / " + nextLevelXP + " XP"
        );

        xpProgress.setMax(
                nextLevelXP
        );

        xpProgress.setProgress(
                Math.min(
                        xp,
                        nextLevelXP
                )
        );
    }

    private void loadMoodStats() {

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor countCursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM mood_entries",
                        null
                );

        int checkIns = 0;

        if (countCursor.moveToFirst()) {
            checkIns =
                    countCursor.getInt(0);
        }

        countCursor.close();

        checkInsText.setText(
                "Check-ins: " + checkIns
        );

        Cursor moodCursor =
                db.rawQuery(
                        "SELECT mood, COUNT(*) AS total " +
                                "FROM mood_entries " +
                                "GROUP BY mood " +
                                "ORDER BY total DESC LIMIT 1",
                        null
                );

        if (moodCursor.moveToFirst()) {

            String mood =
                    moodCursor.getString(
                            moodCursor.getColumnIndexOrThrow(
                                    "mood"
                            )
                    );

            commonMoodText.setText(
                    "Most common mood: " + mood
            );

        } else {

            commonMoodText.setText(
                    "Most common mood: —"
            );
        }

        moodCursor.close();
        db.close();
    }

    private void loadChallenges() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                );

        int completed =
                preferences.getInt(
                        "challengeCount",
                        0
                );

        challengesText.setText(
                "Challenges completed: " + completed
        );
    }

    private void loadAchievements() {

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM mood_entries",
                        null
                );

        int checkIns = 0;

        if (cursor.moveToFirst()) {
            checkIns =
                    cursor.getInt(0);
        }

        cursor.close();
        db.close();

        SharedPreferences preferences =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                );

        int challenges =
                preferences.getInt(
                        "challengeCount",
                        0
                );

        if (challenges >= 7) {

            achievementText.setText(
                    "🏆 Challenge Regular\n" +
                            "Completed 7 daily challenges"
            );

        } else if (challenges >= 3) {

            achievementText.setText(
                    "⭐ Challenge Starter\n" +
                            "Completed 3 daily challenges"
            );

        } else if (checkIns >= 5) {

            achievementText.setText(
                    "🌿 Checking In\n" +
                            "Saved 5 mood check-ins"
            );

        } else if (checkIns >= 1) {

            achievementText.setText(
                    "🌱 First Step\n" +
                            "Saved your first mood check-in"
            );

        } else {

            achievementText.setText(
                    "🌱 Your first achievement is waiting."
            );
        }
    }

    private void loadStreak() {

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT DISTINCT date " +
                                "FROM mood_entries " +
                                "WHERE date IS NOT NULL " +
                                "AND date != ''",
                        null
                );

        java.util.ArrayList<String> dates =
                new java.util.ArrayList<>();

        while (cursor.moveToNext()) {

            String date =
                    cursor.getString(0);

            if (!dates.contains(date)) {
                dates.add(date);
            }
        }

        cursor.close();
        db.close();

        if (dates.size() == 0) {

            streakText.setText("0");
            return;
        }

        SimpleDateFormat format =
                new SimpleDateFormat(
                        "dd-MM-yyyy",
                        Locale.getDefault()
                );

        Calendar current =
                Calendar.getInstance();

        int streak = 0;

        while (true) {

            String today =
                    format.format(
                            current.getTime()
                    );

            if (dates.contains(today)) {

                streak++;

                current.add(
                        Calendar.DAY_OF_YEAR,
                        -1
                );

            } else {

                break;
            }
        }

        streakText.setText(
                String.valueOf(streak)
        );
    }
}