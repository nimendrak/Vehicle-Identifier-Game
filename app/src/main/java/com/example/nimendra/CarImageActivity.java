package com.example.nimendra;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.ValidateImages;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.Styles;

import java.util.Locale;

public class CarImageActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarImageActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;

    private int id;
    private Button identifyBtn;
    private boolean switchStats;
    private TextView timerTextView;

    private static final long START_TIME_IN_MILLIS = 20000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        // This class will load all the images into two different arrays
        // 1 -> car img arr, 2 -> car logo arr
        imageLoader = new ImageLoader(this);

        // This class will do the validations according to imageLoader
        validateImages = new ValidateImages(CarImageActivity.this, this, imageLoader);

        // This class will do the necessary styling to the current activity
        styles = new Styles(CarImageActivity.this, this);

        // This class will handle the populating the Activity
        // ImageViews, TextViews etc
        populateData = new PopulateData(this, imageLoader, styles);

        // Get the switch_stats from MainActivity
        switchStats = getIntent().getExtras().getBoolean("switch_stats");

        // Countdown digits holder
        timerTextView = findViewById(R.id.timer);

        // Check whether switcher is on or off
        // And start the timer accordingly
        if (switchStats) {
            startTimer();
        } else {
            timerTextView.setVisibility(View.INVISIBLE);
        }

        identifyBtn = findViewById(R.id.identify_btn);
        onNextBtnClick();
    }

    View view;
    @SuppressLint("NonConstantResourceId")
    public void validateAnswer(View view) {
        TextView randomCarMake = styles.getRandomCarMake();
        String randomCarMakeStr = (String) randomCarMake.getText();

        // Based on the user input selected image will validate
        // Either answer is correct or wrong, correct answer will marked
        try {
            id = view.getId();
            switch (id) {
                case R.id.car_img1:
                case R.id.car_img2:
                case R.id.car_img3:
                    if (validateImages.validation(id, randomCarMakeStr, populateData)) {
                        styles.correctAnswer(randomCarMakeStr);
                        styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                    } else {
                        styles.wrongAnswer(randomCarMakeStr, id);
                        styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                    }
                    break;

            }
        // In case, user selects nothings correct answer will automatically show
        } catch (Exception e) {
            e.printStackTrace();
            styles.wrongAnswer(randomCarMakeStr, id);
            styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
        }

        // Also, if the switcher is on
        // Countdown will freeze as well
        if (switchStats) {
            pauseTimer();
        }

        onNextBtnClick();
    }

    public void onNextBtnClick() {
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateData.setImagesTaskThree();
                styles.resetAnswer();

                // If user does nothing for next 20s,
                // Program will automatically submit an answer
                if (switchStats) {
                    resetTimer();
                    startTimer();
                }
            }
        });
    }

    // CountDownTimer start when the switchStats == true
    // onFinish() it automatically calls the validating method
    public void startTimer() {
        countDownTimer = new android.os.CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                validateAnswer(view);
            }
        }.start();
    }

    public void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
    }

    public void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        if (seconds <= 10) {
            timerTextView.setTextColor(Color.parseColor("#ff0024"));
        } else {
            timerTextView.setTextColor(Color.WHITE);
        }
        timerTextView.setText(timeLeftFormatted);
    }
}