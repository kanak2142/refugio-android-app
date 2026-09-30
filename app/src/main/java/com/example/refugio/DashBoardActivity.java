package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);

        TextView greetingText =
                findViewById(R.id.greetingText);

        MaterialCardView calmCard =
                findViewById(R.id.calmCard);

        MaterialCardView checkInCard =
                findViewById(R.id.checkInCard);

        MaterialCardView reflectCard =
                findViewById(R.id.reflectCard);

        MaterialCardView routineCard =
                findViewById(R.id.routineCard);

        String name =
                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                ).getString(
                        "name",
                        ""
                );

        if (!name.isEmpty()) {

            greetingText.setText(
                    "Hello, " + name + " 🌿"
            );
        }

        calmCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashBoardActivity.this,
                            CalmActivity.class
                    );

            startActivity(intent);
        });

        checkInCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashBoardActivity.this,
                            MoodLogActivity.class
                    );

            startActivity(intent);
        });

        reflectCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashBoardActivity.this,
                            MoodHistoryActivity.class
                    );

            startActivity(intent);
        });

        routineCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashBoardActivity.this,
                            RoutineActivity.class
                    );

            startActivity(intent);
        });
    }
}