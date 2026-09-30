package com.example.refugio;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class MoodHistoryActivity extends AppCompatActivity {

    private MoodDatabaseHelper databaseHelper;
    private LinearLayout historyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mood_history);

        databaseHelper =
                new MoodDatabaseHelper(this);

        ImageView backButton =
                findViewById(R.id.backButton);

        historyContainer =
                findViewById(R.id.historyContainer);

        backButton.setOnClickListener(v -> finish());

        loadHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (historyContainer != null) {
            loadHistory();
        }
    }

    private void loadHistory() {

        historyContainer.removeAllViews();

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT id, mood, anxiety, trigger_text, note, date " +
                                "FROM mood_entries " +
                                "ORDER BY id DESC",
                        null
                );

        if (!cursor.moveToFirst()) {

            TextView emptyMessage =
                    new TextView(this);

            emptyMessage.setText(
                    "No mood entries yet.\n\n" +
                            "Your saved check-ins will appear here."
            );

            emptyMessage.setTextSize(16);
            emptyMessage.setTextColor(0xFF617A82);
            emptyMessage.setGravity(Gravity.CENTER);
            emptyMessage.setPadding(
                    10,
                    40,
                    10,
                    40
            );

            historyContainer.addView(
                    emptyMessage
            );

            cursor.close();
            db.close();

            return;
        }

        do {

            long id =
                    cursor.getLong(
                            cursor.getColumnIndexOrThrow("id")
                    );

            String mood =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("mood")
                    );

            int anxiety =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("anxiety")
                    );

            String trigger =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("trigger_text")
                    );

            String note =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("note")
                    );

            String date =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("date")
                    );

            addEntry(
                    id,
                    mood,
                    anxiety,
                    trigger,
                    note,
                    date
            );

        } while (cursor.moveToNext());

        cursor.close();
        db.close();
    }

    private void addEntry(
            long id,
            String mood,
            int anxiety,
            String trigger,
            String note,
            String date) {

        MaterialCardView card =
                new MaterialCardView(this);

        card.setCardBackgroundColor(
                0xFFFFFFFF
        );

        card.setRadius(20);

        card.setCardElevation(2);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(
                0,
                0,
                0,
                16
        );

        card.setLayoutParams(
                cardParams
        );

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                20,
                20,
                20,
                16
        );

        TextView moodText =
                createText(
                        mood,
                        21,
                        0xFF806779
                );

        layout.addView(
                moodText
        );

        TextView dateText =
                createText(
                        date,
                        12,
                        0xFF7B8987
                );

        layout.addView(
                dateText
        );

        if (!trigger.isEmpty()) {

            TextView triggerText =
                    createText(
                            "Trigger: " + trigger,
                            14,
                            0xFF526765
                    );

            triggerText.setPadding(
                    0,
                    12,
                    0,
                    0
            );

            layout.addView(
                    triggerText
            );
        }

        if (!note.isEmpty()) {

            TextView noteText =
                    createText(
                            note,
                            15,
                            0xFF4C5554
                    );

            noteText.setPadding(
                    0,
                    10,
                    0,
                    0
            );

            layout.addView(
                    noteText
            );
        }

        Button deleteButton =
                new Button(this);

        deleteButton.setText(
                "Delete"
        );

        deleteButton.setTextSize(
                13
        );

        deleteButton.setTextColor(
                0xFF806779
        );

        deleteButton.setOnClickListener(
                v -> showDeleteDialog(id)
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        buttonParams.gravity =
                Gravity.END;

        deleteButton.setLayoutParams(
                buttonParams
        );

        layout.addView(
                deleteButton
        );

        card.addView(
                layout
        );

        card.setOnClickListener(
                v -> showEntryDialog(
                        mood,
                        anxiety,
                        trigger,
                        note,
                        date
                )
        );

        historyContainer.addView(
                card
        );
    }

    private void showEntryDialog(
            String mood,
            int anxiety,
            String trigger,
            String note,
            String date) {

        StringBuilder message =
                new StringBuilder();

        message.append(
                "Date\n"
        );

        message.append(
                date
        );

        message.append(
                "\n\nAnxiety level\n"
        );

        message.append(
                anxiety
        );

        if (!trigger.isEmpty()) {

            message.append(
                    "\n\nTrigger\n"
            );

            message.append(
                    trigger
            );
        }

        if (!note.isEmpty()) {

            message.append(
                    "\n\nNote\n"
            );

            message.append(
                    note
            );
        }

        if (trigger.isEmpty() &&
                note.isEmpty()) {

            message.append(
                    "\n\nNo additional note was added."
            );
        }

        new AlertDialog.Builder(this)
                .setTitle(mood)
                .setMessage(
                        message.toString()
                )
                .setPositiveButton(
                        "Close",
                        null
                )
                .show();
    }

    private void showDeleteDialog(long id) {

        new AlertDialog.Builder(this)
                .setTitle(
                        "Delete mood entry?"
                )
                .setMessage(
                        "This entry will be removed from your history."
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) ->
                                deleteEntry(id)
                )
                .show();
    }

    private void deleteEntry(long id) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        db.delete(
                "mood_entries",
                "id = ?",
                new String[]{
                        String.valueOf(id)
                }
        );

        db.close();

        loadHistory();
    }

    private TextView createText(
            String text,
            int size,
            int color) {

        TextView textView =
                new TextView(this);

        textView.setText(
                text
        );

        textView.setTextSize(
                size
        );

        textView.setTextColor(
                color
        );

        return textView;
    }
}