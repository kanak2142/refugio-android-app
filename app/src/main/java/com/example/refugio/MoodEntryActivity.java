package com.example.refugio;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoodEntryActivity extends AppCompatActivity {

    MoodDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_entry);

        databaseHelper =
                new MoodDatabaseHelper(this);

        ImageView backButton =
                findViewById(R.id.backButton);

        TextView moodText =
                findViewById(R.id.moodText);

        TextView dateText =
                findViewById(R.id.dateText);

        EditText journalInput =
                findViewById(R.id.journalInput);

        EditText triggerInput =
                findViewById(R.id.triggerInput);

        TextView saveButton =
                findViewById(R.id.saveEntryButton);

        backButton.setOnClickListener(v -> finish());

        String mood =
                getIntent().getStringExtra("selectedMood");

        if (mood != null) {
            moodText.setText(mood);
        }

        String currentDate =
                new SimpleDateFormat(
                        "MMMM dd, yyyy",
                        Locale.getDefault()
                ).format(new Date());

        dateText.setText(currentDate);

        saveButton.setOnClickListener(v -> {

            String note =
                    journalInput
                            .getText()
                            .toString()
                            .trim();

            String trigger =
                    triggerInput
                            .getText()
                            .toString()
                            .trim();

            if (mood == null || mood.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please select a mood",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            SQLiteDatabase db =
                    databaseHelper.getWritableDatabase();

            ContentValues values =
                    new ContentValues();

            values.put("mood", mood);
            values.put("trigger_text", trigger);
            values.put("note", note);

            String date =
                    new SimpleDateFormat(
                            "dd-MM-yyyy",
                            Locale.getDefault()
                    ).format(new Date());

            values.put("date", date);

            long result =
                    db.insert(
                            "mood_entries",
                            null,
                            values
                    );

            db.close();

            if (result != -1) {

                Toast.makeText(
                        this,
                        "Mood saved",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(
                                MoodEntryActivity.this,
                                MoodHistoryActivity.class
                        );

                startActivity(intent);
                finish();

            } else {

                Toast.makeText(
                        this,
                        "Could not save mood",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}