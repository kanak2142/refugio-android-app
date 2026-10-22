package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    private boolean dashboardOpened = false;

    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable openDashboard = new Runnable() {
        @Override
        public void run() {
            goToDashboard();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // Sparkle animation
        TextView sparkle = findViewById(R.id.sparkle);

        Animation sparkleAnimation =
                AnimationUtils.loadAnimation(this, R.anim.sparkel_glow);

        sparkle.startAnimation(sparkleAnimation);

        // Welcome screen click
        View welcomeScreen = findViewById(R.id.welcomeMain);

        welcomeScreen.setOnClickListener(v -> {
            goToDashboard();
        });




        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.welcomeMain),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

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

    private void goToDashboard() {

        // Prevent Dashboard from opening twice
        if (dashboardOpened) {
            return;
        }

        dashboardOpened = true;

        // Cancel the automatic timer
        handler.removeCallbacks(openDashboard);

        // Open Dashboard
        Intent intent =
                new Intent(WelcomeActivity.this, DashBoardActivity.class);

        startActivity(intent);

        // Prevent returning to Welcome screen
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove pending timer
        handler.removeCallbacks(openDashboard);
    }
}