package com.example.nimendra.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

public class Timer {
    private static final long START_TIME_IN_MILLIS = 20000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private final TextView textViewCountDown;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;

    public Timer(TextView textView) {
        this.textViewCountDown = textView;
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

    public void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        if (seconds <= 10) {
            textViewCountDown.setTextColor(Color.parseColor("#ff0024"));
        } else {
            textViewCountDown.setTextColor(Color.WHITE);
        }
        textViewCountDown.setText(timeLeftFormatted);
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }
}
