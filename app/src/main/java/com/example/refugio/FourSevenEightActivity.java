package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class FourSevenEightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_four_seven_eight);

        ImageView backButton = findViewById(R.id.backButton);
        MaterialButton startButton = findViewById(R.id.startButton);

        // Back
        backButton.setOnClickListener(v -> finish());

        // Start 4-7-8
        startButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    FourSevenEightActivity.this,
                    BreathingSessionActivity.class
            );

            intent.putExtra("patternName", "4-7-8 Breathing");
            intent.putExtra("inhale", 4);
            intent.putExtra("hold", 7);
            intent.putExtra("exhale", 8);
            intent.putExtra("rest", 4);

            startActivity(intent);
        });
    }
}