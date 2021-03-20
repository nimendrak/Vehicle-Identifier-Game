package com.example.nimendra;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.Styles;
import com.example.nimendra.utils.ValidateImages;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class CarHintActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarHintActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;

    private TextView timerTextView;
    private TextView attemptCount;

    private Button nextBtn;

    private EditText inputChar;
    private Editable inputCharStr;
    private boolean switchStats;

    private int attempts = 3;

    private static final long START_TIME_IN_MILLIS = 20000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer countDownTimer;

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

        // Countdown digits holder
        timerTextView = findViewById(R.id.timer);

        // Check whether switcher is on or off
        // And start the timer accordingly
        if (switchStats) {
            startTimer();
        } else {
            timerTextView.setVisibility(View.INVISIBLE);
        }

        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        // Once the keyboard imOption click, program will get the current input
        // And holds it in a variable named, inputCharStr
        // Finally, soft keyboard will disappear
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
    }

    View v;

    @SuppressLint("DefaultLocale")
    public void submitAnswer(View view) {
        try {
            String correctAnswer = validateImages.getCorrectCarMakeTaskThree();

            // if switchStats is on countdown will run accordingly to the user actions
            if (attempts > 0) {
                pauseTimer();
                resetTimer();
                startTimer();

                if (!validateImages.validation(inputCharStr)) {
                    attempts = attempts - 1;
                }
                if (correctAnswer != null) {
                    styles.correctAnswer(correctAnswer);
                    attempts = 3;
                    pauseTimer();
                    nextBtnAction();
                }
            } else {
                styles.wrongAnswer(validateImages.getCorrectCarMakeTaskTwo());
                pauseTimer();
                nextBtnAction();
            }

            attemptCount.setText(String.format("%02d", attempts));
            inputChar.getText().clear();

            // If user submit without entering any characters
            // SnackBar will appear with a suitable error message
        } catch (Exception e) {
            e.printStackTrace();

            Snackbar snackbar = Snackbar.make(findViewById(R.id.hint_activity), "Please Prompt a Letter to Proceed!", Snackbar.LENGTH_LONG);
            snackbar.setDuration(2500);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.show();
        }
    }

    // CountDownTimer start when the switchStats == true
    // onFinish() it automatically calls the validating method
    public void startTimer() {
        if (switchStats)
            countDownTimer = new android.os.CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    submitAnswer(v);
                }
            }.start();
    }

    public void resetTimer() {
        if (switchStats) {
            timeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
        }
    }

    public void pauseTimer() {
        if (switchStats) {
            countDownTimer.cancel();
        }
    }

    public void updateCountDownText() {
        if (switchStats) {
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

    public void nextBtnAction() {
        ImageView currentImg = findViewById(R.id.car_image);
        String currentImgText = (String) currentImg.getTag();

        validateImages.removeImgArr(currentImgText);
        inputChar.getText().clear();
        inputCharStr = null;

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateData.setImagesTaskTwo();
                styles.resetAnswer();
                attempts = 3;

                pauseTimer();
                resetTimer();
                startTimer();

            }
        });
    }
}