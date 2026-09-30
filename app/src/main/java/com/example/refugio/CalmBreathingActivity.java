package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class CalmBreathingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calm_breathing);

        ImageView backButton = findViewById(R.id.backButton);
        MaterialButton startButton = findViewById(R.id.startButton);

        // Back
        backButton.setOnClickListener(v -> finish());

        // Start Calm Breathing
        startButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CalmBreathingActivity.this,
                    BreathingSessionActivity.class
            );

            intent.putExtra("patternName", "Calm Breathing");
            intent.putExtra("inhale", 4);
            intent.putExtra("hold", 4);
            intent.putExtra("exhale", 6);
            intent.putExtra("rest", 2);

            startActivity(intent);
        });
    }
}