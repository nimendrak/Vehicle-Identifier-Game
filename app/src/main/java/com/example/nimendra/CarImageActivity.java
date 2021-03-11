package com.example.nimendra;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.Timer;
import com.example.nimendra.utils.ValidateImages;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.Styles;

public class CarImageActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarImageActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;
    private Timer timer;

    private int id;
    private boolean isValidated;
    private Button identifyBtn;
    private String randomCarMakeStr;
    private boolean switchStats;
    private TextView timerTextView;

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
        Log.d(LOG_TAG, "switch stats -> " + switchStats);

        // Countdown digits holder
        timerTextView = findViewById(R.id.timer);

        // Check whether switcher is on or off
        // And start the timer accordingly
        if (switchStats) {
            timer = new Timer(timerTextView);
        }

        identifyBtn = findViewById(R.id.identify_btn);
        onNextBtnClick();

        handlerConfig(null);
    }

    @SuppressLint("NonConstantResourceId")
    public void validateAnswer(View view) {
        TextView randomCarMake = styles.getRandomCarMake();
        randomCarMakeStr = (String) randomCarMake.getText();

        try {
            id = view.getId();
            switch (id) {
                case R.id.car_img1:
                case R.id.car_img2:
                case R.id.car_img3:
                    if (validateImages.validation(id, randomCarMakeStr, populateData)) {
                        styles.correctAnswer(randomCarMakeStr);
                        styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                        Log.d(LOG_TAG, "in validate() -> correct");
                    } else {
                        styles.wrongAnswer(randomCarMakeStr, id);
                        styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                        Log.d(LOG_TAG, "in validate() -> wrong");
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
            styles.wrongAnswer(randomCarMakeStr, id);
            styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
            Log.d(LOG_TAG, "ex null in validate() -> wrong");
        }

        Log.d(LOG_TAG, "car img arr size -> " + imageLoader.getCarImagesArray().size());

        // Also, if the switcher is on
        // Countdown will freeze as well
        if (switchStats) {
            Log.d(LOG_TAG, "time paused");
            timer.pauseTimer();
        }

        onNextBtnClick();
    }

    public void onNextBtnClick() {
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateData.setImagesTaskThree();
                styles.resetAnswer();

                handlerConfig(null);
            }
        });
    }

    // If the switcher is on
    // handlerConfig lets countdown run for 20s
    // And automatically submit the current answer
    public void handlerConfig(final View view) {
        // Check whether switch is on or off
        if (switchStats) {
            Log.d(LOG_TAG, "handleConfig() running..");
            // Reset the time and restart it
            timer.pauseTimer();
            timer.resetTimer();
            timer.startTimer();

            // After 20s, answer will automatically validated
            // and prompter will be styled accordingly
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    styles.correctAnswer(randomCarMakeStr);
                    styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));

                }
            }, 10000);
            // If switcher is off, countdown text view will stay hidden
        } else {
            timerTextView.setVisibility(View.INVISIBLE);
        }
    }
}