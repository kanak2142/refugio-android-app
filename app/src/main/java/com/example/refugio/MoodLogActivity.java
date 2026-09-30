package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MoodLogActivity extends AppCompatActivity {

    private MaterialCardView peacefulCard;
    private MaterialCardView goodCard;
    private MaterialCardView okayCard;
    private MaterialCardView tiredCard;
    private MaterialCardView worriedCard;
    private MaterialCardView stressedCard;
    private MaterialCardView lowCard;
    private MaterialCardView angryCard;

    private MaterialCardView selectedCard;
    private String selectedMood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_log);

        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        peacefulCard = findViewById(R.id.peacefulCard);
        goodCard = findViewById(R.id.goodCard);
        okayCard = findViewById(R.id.okayCard);
        tiredCard = findViewById(R.id.tiredCard);
        worriedCard = findViewById(R.id.worriedCard);
        stressedCard = findViewById(R.id.stressedCard);
        lowCard = findViewById(R.id.lowCard);
        angryCard = findViewById(R.id.angryCard);

        peacefulCard.setOnClickListener(v ->
                selectMood(peacefulCard, "Peaceful"));

        goodCard.setOnClickListener(v ->
                selectMood(goodCard, "Good"));

        okayCard.setOnClickListener(v ->
                selectMood(okayCard, "Okay"));

        tiredCard.setOnClickListener(v ->
                selectMood(tiredCard, "Tired"));

        worriedCard.setOnClickListener(v ->
                selectMood(worriedCard, "Worried"));

        stressedCard.setOnClickListener(v ->
                selectMood(stressedCard, "Stressed"));

        lowCard.setOnClickListener(v ->
                selectMood(lowCard, "Low"));

        angryCard.setOnClickListener(v ->
                selectMood(angryCard, "Angry"));

        MaterialButton continueButton =
                findViewById(R.id.continueButton);

        continueButton.setOnClickListener(v -> {

            if (selectedMood.isEmpty()) {

                Toast.makeText(
                        MoodLogActivity.this,
                        "Choose a mood first",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Intent intent = new Intent(
                    MoodLogActivity.this,
                    MoodEntryActivity.class
            );

            intent.putExtra("selectedMood", selectedMood);

            startActivity(intent);
        });

        MaterialButton historyButton =
                findViewById(R.id.historyButton);

        historyButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MoodLogActivity.this,
                    MoodHistoryActivity.class
            );

            startActivity(intent);
        });
    }

    private void selectMood(
            MaterialCardView card,
            String mood) {

        if (selectedCard != null) {
            selectedCard.setStrokeWidth(1);
        }

        selectedCard = card;
        selectedMood = mood;

        card.setStrokeWidth(4);
    }
}