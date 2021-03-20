package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.Styles;
import com.example.nimendra.utils.ValidateImages;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class AdvancedActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = AdvancedActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;

    private EditText answerOneHolder;
    private EditText answerTwoHolder;
    private EditText answerThreeHolder;

    private Editable[] answerOne;
    private Editable[] answerTwo;
    private Editable[] answerThree;

    private TextView attemptsCount;
    private boolean switchStats;
    private TextView timerTextView;
    private InputMethodManager imm;

    private int correctAns = 0;
    private int attempts = 3;
    private int score = 0;

    private static final long START_TIME_IN_MILLIS = 20000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        // This class will load all the images into two different arrays
        // 1 -> car img arr, 2 -> car logo arr
        imageLoader = new ImageLoader(this);

        // This class will do the validations according to imageLoader
        validateImages = new ValidateImages(AdvancedActivity.this, this, imageLoader);

        // This class will do the necessary styling to the current activity
        styles = new Styles(AdvancedActivity.this, this);

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

        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        answerOneHolder = findViewById(R.id.answer1_input);
        answerOne = getInput(answerOneHolder);
        clearAndReset(answerOneHolder);

        answerTwoHolder = findViewById(R.id.answer2_input);
        answerTwo = getInput(answerTwoHolder);
        clearAndReset(answerTwoHolder);

        answerThreeHolder = findViewById(R.id.answer3_input);
        answerThree = getInput(answerThreeHolder);
        clearAndReset(answerThreeHolder);
    }

    View view;

    @SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
    public void validateAnswer(View view) {
        attemptsCount = findViewById(R.id.car_id);
        TextView scoreView = findViewById(R.id.score);

        View separatorImgOne = findViewById(R.id.separator_img1);
        View separatorImgTwo = findViewById(R.id.separator_img2);
        View separatorImgThree = findViewById(R.id.separator_img3);

        try {
            // Validate each inputs and hold them in unique variables
            // Return values of the validating method as follows;
            // 0 -> correct answer for 1st EditText
            // 1 -> correct answer for 2nd EditText
            // 2 -> correct answer for 3rd EditText
            int validatedAnswerOne = validateImages.validation(answerOne[0].toString(), populateData);
            int validatedAnswerTwo = validateImages.validation(answerTwo[0].toString(), populateData);
            int validatedAnswerThree = validateImages.validation(answerThree[0].toString(), populateData);

            if (attempts > 0) {
                // After user inputs a correct answer
                // That specific will be disable in the future attempts
                if (answerOneHolder.isEnabled()) {
                    if (validatedAnswerOne == 0) {
                        // Styling and disabling
                        correctAnswer(answerOneHolder, separatorImgOne);
                    } else {
                        // Styling
                        wrongAnswer(answerOneHolder, separatorImgOne);
                    }
                }

                if (answerTwoHolder.isEnabled()) {
                    if (validatedAnswerTwo == 1) {
                        correctAnswer(answerTwoHolder, separatorImgTwo);
                    } else {
                        wrongAnswer(answerTwoHolder, separatorImgTwo);
                    }
                }

                if (answerThreeHolder.isEnabled()) {
                    if (validatedAnswerThree == 2) {
                        correctAnswer(answerThreeHolder, separatorImgThree);
                    } else {
                        wrongAnswer(answerThreeHolder, separatorImgThree);
                    }
                }

                // if all the answer are correct, CorrectAnswer gif will pop up
                if (validatedAnswerOne == 0 & validatedAnswerTwo == 1 & validatedAnswerThree == 2) {
                    styles.correctAnswer(String.format("%02d", correctAns) + "/03");
                    nextAction();
                } else {
                    attempts = attempts - 1;
                }

                // at least one of the answer are incorrect, WrongAnswer gif will pop up
            } else {
                styles.wrongAnswer(String.format("%02d", (3 - correctAns)) + "/03");

                if (validatedAnswerOne != 0) {
                    // Show correct answer for 1st EditText in the 1st TextView
                    styles.setCorrectAnswerImgOne(validateImages, populateData);
                }
                if (validatedAnswerTwo != 1) {
                    // Show correct answer for 2nd EditText in the 2nd TextView
                    styles.setCorrectAnswerImgTwo(validateImages, populateData);
                }
                if (validatedAnswerThree != 2) {
                    // Show correct answer for 3rd EditText in the 3rd TextView
                    styles.setCorrectAnswerImgThree(validateImages, populateData);
                }
                nextAction();
            }

            // If user does nothing for next 20s,
            // Program will automatically submit an answer
            pauseTimer();

            attemptsCount.setText(String.format("%02d", attempts));
            scoreView.setText(String.format("%02d", score));

        } catch (Exception e) {
            e.printStackTrace();

            Snackbar snackbar = Snackbar.make(findViewById(R.id.advanced_activity), "Enter all car makes to Proceed!", Snackbar.LENGTH_LONG);
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

    // If user prompted a correct answer,
    // EditText will change as follows
    // And score += 1 and correctAns += 1
    public void correctAnswer(EditText holder, View separator) {
        holder.setBackgroundResource(R.drawable.correct_answer);
        holder.setEnabled(false);
        correctAns++;
        score++;
        separator.setVisibility(View.INVISIBLE);
    }

    // If user prompted a wrong answer,
    // EditText will change as follows
    public void wrongAnswer(EditText holder, View separator) {
        holder.setBackgroundResource(R.drawable.wrong_answer);
        separator.setBackgroundColor(Color.parseColor("#FFFF615B"));
        separator.setVisibility(View.VISIBLE);
    }

    // Clear the clicking EditText and restart the timer
    public void clearAndReset(final EditText holder) {
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getText().clear();
                pauseTimer();
                resetTimer();
                startTimer();
            }
        });
    }

    // Next button will remove the current 3 images
    // Set the variables to null
    // and reset the current activity for new turn
    public void nextAction() {
        validateImages.removeImgArr();

        Button nextBtn = findViewById(R.id.next_btn);

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                answerOne[0] = null;
                answerTwo[0] = null;
                answerThree[0] = null;

                attempts = 3;
                correctAns = 0;
                styles.resetAnswer();
                populateData.setImagesTaskFour();
                attemptsCount.setText(String.format("%02d", attempts));

                // If user does nothing for next 20s,
                // Program will automatically submit an answer
                pauseTimer();
                resetTimer();
                startTimer();

            }
        });
    }

    // Get input from the EditText and holds it on a array of 1 element
    // Once user clicks the submit button, soft keyboard will disappear
    public Editable[] getInput(final EditText holder) {
        final Editable[] answer = new Editable[1];

        if (holder != null) {
            holder.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                answer[0] = holder.getText();
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                handled = true;
                            }
                            return handled;
                        }
                    });

        }
        return answer;
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
                    validateAnswer(view);
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

}