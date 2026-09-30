package com.example.refugio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class CustomBreathingActivity extends AppCompatActivity {

    private EditText inhaleInput;
    private EditText holdInput;
    private EditText exhaleInput;
    private EditText restInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_breathing);

        ImageView backButton =
                findViewById(R.id.backButton);

        inhaleInput =
                findViewById(R.id.inhaleInput);

        holdInput =
                findViewById(R.id.holdInput);

        exhaleInput =
                findViewById(R.id.exhaleInput);

        restInput =
                findViewById(R.id.restInput);

        MaterialButton startButton =
                findViewById(R.id.startCustomButton);


        // Back
        backButton.setOnClickListener(v -> finish());


        // Start custom breathing
        startButton.setOnClickListener(v -> {

            int inhale = getNumber(inhaleInput, 4);
            int hold = getNumber(holdInput, 4);
            int exhale = getNumber(exhaleInput, 6);
            int rest = getNumber(restInput, 2);

            Intent intent = new Intent(
                    CustomBreathingActivity.this,
                    BreathingSessionActivity.class
            );

            intent.putExtra("patternName", "Your Breathing");
            intent.putExtra("inhale", inhale);
            intent.putExtra("hold", hold);
            intent.putExtra("exhale", exhale);
            intent.putExtra("rest", rest);

            startActivity(intent);
        });
    }


    private int getNumber(EditText input, int defaultValue) {

        String value =
                input.getText().toString().trim();

        if (value.isEmpty()) {
            return defaultValue;
        }

        try {

            int number =
                    Integer.parseInt(value);

            if (number <= 0) {
                return defaultValue;
            }

            return number;

        } catch (Exception e) {

            return defaultValue;
        }
    }
}