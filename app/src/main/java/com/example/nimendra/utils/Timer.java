package com.example.nimendra.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class Timer {

    // Class name for Log tag
    private static final String LOG_TAG = Timer.class.getSimpleName();

    private static final long START_TIME_IN_MILLIS = 10000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private final TextView textViewCountDown;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;

    public Timer(TextView textView) {
        this.textViewCountDown = textView;
        startTimer();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
            }
        }.start();
        timerRunning = true;
    }

    public void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        Log.d(LOG_TAG, "time left -> " + timeLeftFormatted);
        if (seconds <= 5) {
            textViewCountDown.setTextColor(Color.parseColor("#ff0024"));
        } else {
            textViewCountDown.setTextColor(Color.WHITE);
        }
        textViewCountDown.setText(timeLeftFormatted);
    }
}
