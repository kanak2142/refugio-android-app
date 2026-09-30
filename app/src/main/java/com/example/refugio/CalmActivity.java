package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class CalmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calm);

        ImageView backButton =
                findViewById(R.id.backButton);

        MaterialCardView breatheCard =
                findViewById(R.id.breatheCard);

        MaterialCardView groundCard =
                findViewById(R.id.groundCard);

        MaterialCardView quietCard =
                findViewById(R.id.quietCard);

        backButton.setOnClickListener(v -> finish());

        breatheCard.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CalmActivity.this,
                    BreathActivity.class
            );

            startActivity(intent);
        });

        groundCard.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CalmActivity.this,
                    GroundingActivity.class
            );

            startActivity(intent);
        });

        quietCard.setOnClickListener(v -> {
            Intent intent  = new Intent(
                    CalmActivity.this, QuietMomentActivity.class);
            startActivity(intent);

        });
    }
}