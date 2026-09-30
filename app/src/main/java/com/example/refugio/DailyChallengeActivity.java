package com.example.refugio;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DailyChallengeActivity extends AppCompatActivity {

    private MaterialCardView challengeCard;
    private MaterialButton completeButton;
    private TextView challengeEmoji;
    private TextView challengeTitle;
    private TextView challengeDescription;
    private TextView xpText;
    private TextView statusText;

    private String today;

    private String[] titles = {
            "Take a quiet break",
            "Write one good thought",
            "Take five slow breaths",
            "Drink some water",
            "Look away from your screen",
            "Write down one thing you enjoyed",
            "Spend two minutes in silence"
    };

    private String[] descriptions = {
            "Take two minutes away from your work and simply relax.",
            "Write down one small thing that made today better.",
            "Slow down and take five comfortable breaths.",
            "Take a short break and drink a glass of water.",
            "Rest your eyes and look outside or across the room.",
            "Write about one small moment you appreciated today.",
            "Sit quietly for two minutes without checking your phone."
    };

    private String[] emojis = {
            "🌿",
            "✍",
            "🌬",
            "💧",
            "🌤",
            "☀",
            "🌙"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenges);

        ImageView backButton =
                findViewById(R.id.backButton);

        challengeCard =
                findViewById(R.id.challengeCard);

        completeButton =
                findViewById(R.id.completeButton);

        challengeEmoji =
                findViewById(R.id.challengeEmoji);

        challengeTitle =
                findViewById(R.id.challengeTitle);

        challengeDescription =
                findViewById(R.id.challengeDescription);

        xpText =
                findViewById(R.id.xpText);

        statusText =
                findViewById(R.id.statusText);

        backButton.setOnClickListener(
                v -> finish()
        );

        today =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                ).format(new Date());

        showTodayChallenge();

        completeButton.setOnClickListener(
                v -> completeChallenge()
        );
    }

    private void showTodayChallenge() {

        int dayNumber =
                Integer.parseInt(
                        new SimpleDateFormat(
                                "D",
                                Locale.getDefault()
                        ).format(new Date())
                );

        int index =
                (dayNumber - 1) % titles.length;

        challengeEmoji.setText(
                emojis[index]
        );

        challengeTitle.setText(
                titles[index]
        );

        challengeDescription.setText(
                descriptions[index]
        );

        xpText.setText(
                "+10 XP"
        );

        String completedDate =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                ).getString(
                        "challengeDate",
                        ""
                );

        if (today.equals(completedDate)) {

            showCompletedState();

        } else {

            completeButton.setText(
                    "Complete Challenge"
            );

            completeButton.setEnabled(true);

            statusText.setText(
                    "Complete today's challenge to earn 10 XP."
            );
        }
    }

    private void completeChallenge() {

        getSharedPreferences(
                "RefugioData",
                MODE_PRIVATE
        )
                .edit()
                .putString(
                        "challengeDate",
                        today
                )
                .apply();

        int xp =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                ).getInt(
                        "xp",
                        0
                );

        xp = xp + 10;

        int challengeCount =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                ).getInt(
                        "challengeCount",
                        0
                );

        challengeCount++;

        getSharedPreferences(
                "RefugioData",
                MODE_PRIVATE
        )
                .edit()
                .putInt(
                        "xp",
                        xp
                )
                .putInt(
                        "challengeCount",
                        challengeCount
                )
                .apply();

        showCompletedState();
    }

    private void showCompletedState() {

        challengeEmoji.setText(
                "✓"
        );

        challengeTitle.setText(
                "Challenge complete!"
        );

        challengeDescription.setText(
                "You made a little space for yourself today."
        );

        xpText.setText(
                "+10 XP earned"
        );

        completeButton.setText(
                "Completed"
        );

        completeButton.setEnabled(false);

        statusText.setText(
                "Come back tomorrow for another challenge."
        );
    }
}