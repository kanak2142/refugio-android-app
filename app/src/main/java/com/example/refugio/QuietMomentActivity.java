package com.example.refugio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class QuietMomentActivity extends AppCompatActivity {

    private TextView timerText;
    private TextView messageText;
    private TextView subMessageText;
    private TextView bottomText;

    private MaterialButton startButton;
    private MaterialCardView quietCircle;
    private CircularProgressIndicator progressCircle;

    private CountDownTimer timer;
    private MediaPlayer mediaPlayer;

    private boolean running = false;

    private MaterialButton clockButton;
    private MaterialButton wavesButton;
    private MaterialButton breezeButton;
    private MaterialButton silentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quietmoments);

        ImageView backButton =
                findViewById(R.id.backButton);

        timerText =
                findViewById(R.id.timerText);

        messageText =
                findViewById(R.id.messageText);

        subMessageText =
                findViewById(R.id.subMessageText);

        bottomText =
                findViewById(R.id.bottomText);

        startButton =
                findViewById(R.id.startButton);

        quietCircle =
                findViewById(R.id.quietCircle);

        progressCircle =
                findViewById(R.id.progressCircle);

        clockButton =
                findViewById(R.id.clockButton);

        wavesButton =
                findViewById(R.id.wavesButton);

        breezeButton =
                findViewById(R.id.breezeButton);

        silentButton =
                findViewById(R.id.silentButton);

        backButton.setOnClickListener(
                v -> finish()
        );

        startButton.setOnClickListener(v -> {

            if (!running) {
                startQuietMoment();
            } else {
                stopQuietMoment();
            }
        });

        clockButton.setOnClickListener(v ->
                playSound(R.raw.clockticking)
        );

        wavesButton.setOnClickListener(v ->
                playSound(R.raw.soundwaves)
        );

        breezeButton.setOnClickListener(v ->
                playSound(R.raw.morningbreeze)
        );

        silentButton.setOnClickListener(v ->
                stopSound()
        );
    }

    private void startQuietMoment() {

        running = true;

        startButton.setText(
                "End quiet moment"
        );

        bottomText.setText(
                "Stay here for as long as you need."
        );

        messageText.setText(
                "Settle in."
        );

        subMessageText.setText(
                "You don't need to do anything right now."
        );

        startCircleAnimation();

        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(
                    long millisUntilFinished) {

                long seconds =
                        millisUntilFinished / 1000;

                timerText.setText(
                        String.valueOf(seconds)
                );

                progressCircle.setProgress(
                        (int) seconds
                );

                if (seconds == 45) {

                    messageText.setText(
                            "Let your shoulders soften."
                    );

                    subMessageText.setText(
                            "There is nowhere you need to rush to."
                    );

                } else if (seconds == 30) {

                    messageText.setText(
                            "Notice your breathing."
                    );

                    subMessageText.setText(
                            "Let each breath happen naturally."
                    );

                } else if (seconds == 15) {

                    messageText.setText(
                            "Stay here a little longer."
                    );

                    subMessageText.setText(
                            "Just notice the moment around you."
                    );
                }
            }

            @Override
            public void onFinish() {

                running = false;

                timerText.setText("0");

                progressCircle.setProgress(0);

                messageText.setText(
                        "That was your quiet minute."
                );

                subMessageText.setText(
                        "Take that little bit of calm with you."
                );

                startButton.setText(
                        "Begin again"
                );

                bottomText.setText(
                        "You can take another moment whenever you need."
                );

                quietCircle.clearAnimation();
            }

        }.start();
    }

    private void stopQuietMoment() {

        if (timer != null) {
            timer.cancel();
        }

        quietCircle.clearAnimation();

        running = false;

        timerText.setText("60");

        progressCircle.setProgress(60);

        messageText.setText(
                "You can pause here."
        );

        subMessageText.setText(
                "Come back whenever you feel ready."
        );

        startButton.setText(
                "Begin quiet moment"
        );

        bottomText.setText(
                "You can stop whenever you want."
        );
    }

    private void playSound(int soundResource) {

        stopSound();

        mediaPlayer =
                MediaPlayer.create(
                        this,
                        soundResource
                );

        if (mediaPlayer != null) {

            mediaPlayer.setLooping(true);

            mediaPlayer.start();
        }
    }

    private void stopSound() {

        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void startCircleAnimation() {

        ScaleAnimation animation =
                new ScaleAnimation(
                        0.96f,
                        1.04f,
                        0.96f,
                        1.04f,
                        ScaleAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF,
                        0.5f
                );

        animation.setDuration(4000);

        animation.setRepeatMode(
                ScaleAnimation.REVERSE
        );

        animation.setRepeatCount(
                ScaleAnimation.INFINITE
        );

        quietCircle.startAnimation(animation);
    }

    @Override
    protected void onDestroy() {

        if (timer != null) {
            timer.cancel();
        }

        stopSound();

        if (quietCircle != null) {
            quietCircle.clearAnimation();
        }

        super.onDestroy();
    }
}