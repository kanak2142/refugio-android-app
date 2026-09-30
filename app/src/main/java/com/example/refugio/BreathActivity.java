package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class BreathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_breath);

        // Back button
        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());


        // BOX BREATHING
        MaterialCardView featuredBreath =
                findViewById(R.id.featuredBreath);

        featuredBreath.setOnClickListener(v -> {

            Intent intent = new Intent(
                    BreathActivity.this,
                    BreathingSessionActivity.class
            );

            intent.putExtra("patternName", "Box Breathing");
            intent.putExtra("inhale", 4);
            intent.putExtra("hold", 4);
            intent.putExtra("exhale", 4);
            intent.putExtra("rest", 4);

            startActivity(intent);
        });


        // 4-7-8
        MaterialCardView breath478Row =
                findViewById(R.id.breath478Row);

        breath478Row.setOnClickListener(v -> {

            Intent intent = new Intent(
                    BreathActivity.this,
                    FourSevenEightActivity.class
            );

            startActivity(intent);
        });


        // CALM
        MaterialCardView calmBreathingRow =
                findViewById(R.id.calmBreathingRow);

        calmBreathingRow.setOnClickListener(v -> {

            Intent intent = new Intent(
                    BreathActivity.this,
                    CalmBreathingActivity.class
            );

            startActivity(intent);
        });


        // CUSTOM
        MaterialCardView customBreathingRow =
                findViewById(R.id.customBreathingRow);

        customBreathingRow.setOnClickListener(v -> {

            Intent intent = new Intent(
                    BreathActivity.this,
                    CustomBreathingActivity.class
            );

            startActivity(intent);
        });


        // System bars
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.breathMain),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }
}