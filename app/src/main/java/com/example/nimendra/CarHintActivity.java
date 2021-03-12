package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nimendra.utils.CountDownTimer;
import com.example.nimendra.utils.ValidateImages;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.Styles;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CarHintActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarHintActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;
    private CountDownTimer timer;

    private TextView attemptCount;
    private Button nextBtn;

    private EditText inputChar;
    private Editable inputCharStr;
    private boolean switchStats;
    private TextView timerTextView;

    private int attempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        // This class will load all the images into two different arrays
        // 1 -> car img arr, 2 -> car logo arr
        imageLoader = new ImageLoader(this);

        // This class will do the validations according to imageLoader
        validateImages = new ValidateImages(CarHintActivity.this, this, imageLoader);

        // This class will do the necessary styling to the current activity
        styles = new Styles(CarHintActivity.this, this);

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
            timer = new CountDownTimer(timerTextView);
        }

        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputChar = findViewById(R.id.input_char);
        if (inputChar != null) {
            inputChar.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                inputCharStr = inputChar.getText();
                                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                handled = true;
                            }
                            return handled;
                        }
                    });

        }

        attemptCount = findViewById(R.id.car_id);
        nextBtn = findViewById(R.id.next_btn);

        handlerConfig();
    }

    @SuppressLint("DefaultLocale")
    public void submitAnswer(View view) {
        try {
            if (!inputCharStr.toString().equals("")) {
                getInput();
            } else {
                attempts = attempts - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar snackbar = Snackbar.make(view, "Please Prompt a Letter to Proceed!", Snackbar.LENGTH_LONG);
            snackbar.setDuration(2500);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            snackbar.show();
        }
        // Also, if the switcher is on
        // Countdown will freeze as well
        if (switchStats) {
            Log.d(LOG_TAG, "time paused");
            timer.pauseTimer();
            timer.resetTimer();
        }
    }

    // If the switcher is on
    // handlerConfig lets countdown run for 20s
    // And automatically submit the current answer
    public void handlerConfig() {
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
                    getInput();
                }
            }, 10000);
            // If switcher is off, countdown text view will stay hidden
        } else {
            timerTextView.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    public void getInput() {
        String correctAnswer = validateImages.getCorrectCarMakeTaskThree();

        if (attempts > 0) {
            if (!validateImages.validation(inputCharStr, populateData)) {
                attempts = attempts - 1;
            }
            Log.d(LOG_TAG, "correct answer -> " + correctAnswer);
            if (correctAnswer != null) {
                styles.correctAnswer(correctAnswer);
                nextBtn.setVisibility(View.VISIBLE);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        populateData.setImagesTaskTwo();
                        styles.resetAnswer();
                        attempts = 3;
                        handlerConfig();
                    }
                });
            }
        } else {
            styles.wrongAnswer(validateImages.getCorrectCarMakeTaskTwo());

            nextBtn.setVisibility(View.VISIBLE);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populateData.setImagesTaskTwo();
                    styles.resetAnswer();
                    attempts = 3;
                    handlerConfig();
                }
            });
        }
        attemptCount.setText(String.format("%02d", attempts));
        inputChar.setText("");
    }

}