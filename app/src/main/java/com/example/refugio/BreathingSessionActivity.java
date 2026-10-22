package com.example.refugio;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Locale;

public class BreathingSessionActivity extends AppCompatActivity
        implements TextToSpeech.OnInitListener {
    private TextView instructionText;
    private TextView timerText;
    private TextView cycleText;

    private TextView backButton;
    private TextView voiceButton;

    private MaterialButton startButton;
    private MaterialButton pauseButton;

    private CircularProgressIndicator breathProgress; //progress indicator

    private TextView closingMessage;



    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private static final int TOTAL_CYCLES = 4;

    private int currentCycle = 1; //stores the current cycle
    private int currentPhase = 0;//stores current breathing phase

    private long phaseElapsed = 0;// stores the time passed in the current phase

    private boolean isRunning = false;
    private boolean isPaused = false;

    //stores duration of each phase
    private int inhaleTime = 4;
    private int holdTime = 4;
    private int exhaleTime = 4;
    private int restTime = 4;


    private final String[] phases = {
            "Breathe in",
            "Hold",
            "Breathe out",
            "Rest"
    };



    private TextToSpeech textToSpeech;

    private boolean voiceEnabled = true;
    private boolean ttsReady = false;



    private MediaPlayer calmingSound;

    private static final float CALMING_VOLUME = 0.35f;




    private float displayedProgress = 0f; //stores the current progress displayed on the screen





    private final Runnable breathingRunnable = new Runnable() {

        @Override
        public void run() {

            if (!isRunning) {
                return;
            }

            phaseElapsed += 50;

            long phaseDuration =
                    getCurrentPhaseDuration();

            if (phaseElapsed > phaseDuration) {
                phaseElapsed = phaseDuration;
            }

            updateBreathingProgress();

            updateTimer();


            if (phaseElapsed >= phaseDuration) {

                moveToNextPhase();

            } else {

                handler.postDelayed(
                        this,
                        50
                );
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_breathing_session
        );




        instructionText =
                findViewById(R.id.instructionText);

        timerText =
                findViewById(R.id.timerText);

        cycleText =
                findViewById(R.id.cycleText);

        backButton =
                findViewById(R.id.backButton);

        voiceButton =
                findViewById(R.id.voiceButton);

        startButton =
                findViewById(R.id.startButton);

        pauseButton =
                findViewById(R.id.pauseButton);

        breathProgress =
                findViewById(R.id.breathProgress);

        closingMessage =
                findViewById(R.id.closingMessage);




        inhaleTime =
                getIntent().getIntExtra(
                        "inhale",
                        4
                );

        holdTime =
                getIntent().getIntExtra(
                        "hold",
                        4
                );

        exhaleTime =
                getIntent().getIntExtra(
                        "exhale",
                        4
                );

        restTime =
                getIntent().getIntExtra(
                        "rest",
                        4
                );




        breathProgress.setMax(100);

        breathProgress.setProgressCompat(
                0,
                false
        );




        textToSpeech =
                new TextToSpeech(
                        this,
                        this
                );



        setupCalmingSound();


        instructionText.setText("Ready");

        timerText.setText(
                String.valueOf(inhaleTime)
        );

        cycleText.setText(
                "Cycle 1 of 4"
        );

        pauseButton.setVisibility(
                View.GONE
        );

        closingMessage.setText("");

        voiceButton.setText("🔊");


        backButton.setOnClickListener(v -> {
            finish();
        });


        voiceButton.setOnClickListener(v -> {
            toggleVoice();
        });

        startButton.setOnClickListener(v -> {

            if (!isRunning) {
                startSession();
            }

        });

        pauseButton.setOnClickListener(v -> {

            if (isRunning) {

                pauseSession();

            } else if (isPaused) {

                resumeSession();
            }

        });
    }



    private long getCurrentPhaseDuration() {

        if (currentPhase == 0) {

            return inhaleTime * 1000L;

        } else if (currentPhase == 1) {

            return holdTime * 1000L;

        } else if (currentPhase == 2) {

            return exhaleTime * 1000L;

        } else {

            return restTime * 1000L;
        }
    }


    private void setupCalmingSound() {

        try {

            calmingSound =
                    MediaPlayer.create(
                            this,
                            R.raw.calming_sound
                    );


            if (calmingSound != null) {

                AudioAttributes audioAttributes =
                        new AudioAttributes.Builder()
                                .setUsage(
                                        AudioAttributes.USAGE_MEDIA
                                )
                                .setContentType(
                                        AudioAttributes.CONTENT_TYPE_MUSIC
                                )
                                .build();


                calmingSound.setAudioAttributes(
                        audioAttributes
                );


                calmingSound.setLooping(true);


                calmingSound.setVolume(
                        CALMING_VOLUME,
                        CALMING_VOLUME
                );


                calmingSound.setOnErrorListener(
                        (mp, what, extra) -> {

                            return true;
                        }
                );
            }

        } catch (Exception e) {

            calmingSound = null;
        }
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result =
                    textToSpeech.setLanguage(
                            Locale.US
                    );


            if (result !=
                    TextToSpeech.LANG_MISSING_DATA
                    &&
                    result !=
                            TextToSpeech.LANG_NOT_SUPPORTED) {

                ttsReady = true;

                textToSpeech.setSpeechRate(
                        0.72f
                );

                textToSpeech.setPitch(
                        0.90f
                );
            }
        }
    }



    private void startSession() {

        currentCycle = 1;

        currentPhase = 0;

        phaseElapsed = 0;

        displayedProgress = 0f;

        isRunning = true;

        isPaused = false;


        instructionText.setText(
                phases[currentPhase]
        );


        timerText.setText(
                String.valueOf(inhaleTime)
        );


        cycleText.setText(
                "Cycle 1 of 4"
        );


        closingMessage.setText("");


        breathProgress.setProgressCompat(
                0,
                false
        );


        startButton.setVisibility(
                View.GONE
        );


        pauseButton.setVisibility(
                View.VISIBLE
        );


        pauseButton.setText("Pause");


        // MUSIC
        startCalmingSound();




        // VOICE
        speakCurrentPhase();


        handler.removeCallbacks(
                breathingRunnable
        );


        handler.postDelayed(
                breathingRunnable,
                50
        );
    }




    private void pauseSession() {

        isRunning = false;

        isPaused = true;


        handler.removeCallbacks(
                breathingRunnable
        );


        if (calmingSound != null
                && calmingSound.isPlaying()) {

            calmingSound.pause();
        }



        pauseButton.setText("Resume");

        instructionText.setText("Paused");


        if (ttsReady && voiceEnabled) {

            speakText(
                    "Take your time."
            );
        }
    }

    private void resumeSession() {

        isRunning = true;

        isPaused = false;


        instructionText.setText(
                phases[currentPhase]
        );


        pauseButton.setText("Pause");


        startCalmingSound();



        handler.removeCallbacks(
                breathingRunnable
        );


        handler.postDelayed(
                breathingRunnable,
                50
        );
    }


    // =========================================================
    // PROGRESS
    // =========================================================

    private void updateBreathingProgress() {

        long phaseDuration =
                getCurrentPhaseDuration();


        float targetProgress =
                (phaseElapsed * 100f)
                        / phaseDuration;


        if (targetProgress > 100f) {
            targetProgress = 100f;
        }


        displayedProgress =
                displayedProgress
                        + (targetProgress
                        - displayedProgress)
                        * 0.35f;


        int progress =
                Math.round(
                        displayedProgress
                );


        breathProgress.setProgressCompat(
                progress,
                false
        );
    }


    // =========================================================
    // TIMER
    // =========================================================

    private void updateTimer() {

        long phaseDuration =
                getCurrentPhaseDuration();


        long remaining =
                phaseDuration
                        - phaseElapsed;


        int seconds =
                (int) Math.ceil(
                        remaining / 1000.0
                );


        if (seconds < 1) {
            seconds = 1;
        }


        timerText.setText(
                String.valueOf(seconds)
        );
    }


    // =========================================================
    // NEXT PHASE
    // =========================================================

    private void moveToNextPhase() {

        phaseElapsed = 0;

        displayedProgress = 0f;

        currentPhase++;


        if (currentPhase >= phases.length) {

            currentPhase = 0;

            currentCycle++;


            if (currentCycle > TOTAL_CYCLES) {

                finishSession();

                return;
            }
        }


        instructionText.setText(
                phases[currentPhase]
        );


        timerText.setText(
                String.valueOf(
                        getCurrentPhaseDuration()
                                / 1000
                )
        );


        cycleText.setText(
                "Cycle "
                        + currentCycle
                        + " of "
                        + TOTAL_CYCLES
        );


        breathProgress.setProgressCompat(
                0,
                false
        );


        speakCurrentPhase();


        handler.postDelayed(
                breathingRunnable,
                50
        );
    }





    private void speakCurrentPhase() {

        if (!voiceEnabled || !ttsReady) {
            return;
        }


        String message;


        if (currentPhase == 0) {

            message =
                    "Breathe in... slowly.";

        } else if (currentPhase == 1) {

            message =
                    "Hold... gently.";

        } else if (currentPhase == 2) {

            message =
                    "Breathe out... slowly.";

        } else {

            message =
                    "Rest...";
        }


        speakText(message);
    }


    private void speakText(String message) {

        if (!ttsReady || !voiceEnabled) {
            return;
        }


        textToSpeech.setSpeechRate(
                0.72f
        );


        textToSpeech.setPitch(
                0.90f
        );


        textToSpeech.speak(
                message,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "refugio_voice"
        );
    }


    // =========================================================
    // VOICE TOGGLE
    // =========================================================

    private void toggleVoice() {

        voiceEnabled = !voiceEnabled;


        if (voiceEnabled) {

            voiceButton.setText("🔊");


            if (isRunning) {
                speakCurrentPhase();
            }


        } else {

            voiceButton.setText("🔇");


            if (ttsReady) {
                textToSpeech.stop();
            }
        }
    }


    // =========================================================
    // START MUSIC
    // =========================================================

    private void startCalmingSound() {

        if (calmingSound == null) {
            return;
        }


        try {

            if (!calmingSound.isPlaying()) {

                calmingSound.setVolume(
                        CALMING_VOLUME,
                        CALMING_VOLUME
                );


                calmingSound.start();
            }

        } catch (Exception ignored) {
        }
    }


    // =========================================================
    // FINISH
    // =========================================================

    private void finishSession() {

        isRunning = false;

        isPaused = false;


        handler.removeCallbacks(
                breathingRunnable
        );


        breathProgress.setProgressCompat(
                100,
                true
        );


        instructionText.setText(
                "Complete"
        );


        timerText.setText("0");


        cycleText.setText(
                "4 cycles completed"
        );


        pauseButton.setVisibility(
                View.GONE
        );


        startButton.setVisibility(
                View.VISIBLE
        );


        startButton.setText(
                "Begin again"
        );


        closingMessage.setText(
                "Well done. Take one final comfortable breath. 🍃"
        );


        if (ttsReady && voiceEnabled) {

            speakText(
                    "Well done... take one final comfortable breath."
            );
        }


        fadeOutCalmingSound();
    }


    // =========================================================
    // FADE MUSIC
    // =========================================================

    private void fadeOutCalmingSound() {

        if (calmingSound == null
                || !calmingSound.isPlaying()) {

            return;
        }


        final Handler fadeHandler =
                new Handler(
                        Looper.getMainLooper()
                );


        final int steps = 15;

        final int[] step = {0};


        Runnable fadeRunnable =
                new Runnable() {

                    @Override
                    public void run() {

                        step[0]++;


                        float volume =
                                CALMING_VOLUME
                                        * (1f
                                        - (
                                        step[0]
                                                / (float) steps
                                ));


                        if (volume < 0) {
                            volume = 0;
                        }


                        calmingSound.setVolume(
                                volume,
                                volume
                        );


                        if (step[0] < steps) {

                            fadeHandler.postDelayed(
                                    this,
                                    200
                            );

                        } else {

                            calmingSound.pause();


                            calmingSound.setVolume(
                                    CALMING_VOLUME,
                                    CALMING_VOLUME
                            );
                        }
                    }
                };


        fadeHandler.post(
                fadeRunnable
        );
    }

    @Override
    protected void onDestroy() {

        handler.removeCallbacks(
                breathingRunnable
        );


        if (calmingSound != null) {

            try {

                if (calmingSound.isPlaying()) {
                    calmingSound.stop();
                }

                calmingSound.release();

            } catch (Exception ignored) {
            }

            calmingSound = null;
        }


        if (textToSpeech != null) {

            textToSpeech.stop();

            textToSpeech.shutdown();

            textToSpeech = null;
        }


        super.onDestroy();
    }
}