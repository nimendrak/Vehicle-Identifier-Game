package com.example.nimendra.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

public class Timer {
    private static final long START_TIME_IN_MILLIS = 20000;
    private final TextView mTextViewCountDown;
    private boolean timerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    public Timer (TextView textView) {
        this.mTextViewCountDown = textView;
    }

    public void startTimer() {
        CountDownTimer mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
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
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }
}
