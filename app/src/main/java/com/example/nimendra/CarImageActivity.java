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
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;
    private Timer timer;

    private int id;
    private Button identifyBtn;
    private String randomCarMakeStr;
    private boolean switchStats;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        timerTextView = findViewById(R.id.timer);

        timer = new Timer(timerTextView);
        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(CarImageActivity.this, this, imageLoader);
        styles = new Styles(CarImageActivity.this, this);
        populateData = new PopulateData(this, imageLoader, styles);

        // Get the switch_stats from MainActivity
        switchStats = getIntent().getExtras().getBoolean("switch_stats");
        Log.d(LOG_TAG, "switch stats -> " + switchStats);

        identifyBtn = findViewById(R.id.identify_btn);
        onNextBtnClick();

        handlerConfig(null);
    }

    @SuppressLint("NonConstantResourceId")
    public void validateAnswer(View view) {
        TextView randomCarMake = styles.getRandomCarMake();
        randomCarMakeStr = (String) randomCarMake.getText();

        handlerConfig(null);

        Log.d(LOG_TAG, "in validate() -> " + validateImages.validation(id, randomCarMakeStr, populateData));

        try {
            id = view.getId();
            switch (id) {
                case R.id.car_img1:
                case R.id.car_img2:
                case R.id.car_img3:
                    if (validateImages.validation(id, randomCarMakeStr, populateData)) {
                        styles.correctAnswer(randomCarMakeStr);
                        styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                        resetTimer();
                        Log.d(LOG_TAG, "in validate() -> correct");
                    } else {
                        styles.wrongAnswer(randomCarMakeStr, id);
                        styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                        Log.d(LOG_TAG, "in validate() -> wrong");
                    }
                    break;
            }
        } catch (Exception e) {
            styles.wrongAnswer(randomCarMakeStr, id);
            styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
            Log.d(LOG_TAG, "null in validate() -> wrong");
            resetTimer();
        }

        onNextBtnClick();
    }

    public void onNextBtnClick() {
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateData.setImagesTaskThree();
                styles.resetAnswer();
            }
        });
    }

    public void handlerConfig(final View view) {
        if (switchStats) {
            timer.startTimer();

            try {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validateAnswer(view);
                    }
                }, 10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            timerTextView.setVisibility(View.INVISIBLE);
        }
    }

    public void resetTimer() {
        if (switchStats) {
            timer.pauseTimer();
            timer.resetTimer();
        }
    }
}