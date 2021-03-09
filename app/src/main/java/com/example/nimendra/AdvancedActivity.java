package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.Styles;
import com.example.nimendra.utils.ValidateImages;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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

    private Editable answerOne;
    private Editable answerTwo;
    private Editable answerThree;

    private int attempts = 3;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(AdvancedActivity.this, this, imageLoader);
        styles = new Styles(AdvancedActivity.this, this);
        populateData = new PopulateData(this, imageLoader, styles);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        answerOneHolder = findViewById(R.id.answer1_input);
        if (answerOneHolder != null) {
            answerOneHolder.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                answerOne = answerOneHolder.getText();
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                handled = true;
                            }
                            return handled;
                        }
                    });

        }

        answerTwoHolder = findViewById(R.id.answer2_input);
        if (answerTwoHolder != null) {
            answerTwoHolder.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                answerTwo = answerTwoHolder.getText();
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                handled = true;
                            }
                            return handled;
                        }
                    });

        }

        answerThreeHolder = findViewById(R.id.answer3_input);
        if (answerThreeHolder != null) {
            answerThreeHolder.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                answerThree = answerThreeHolder.getText();
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                handled = true;
                            }
                            return handled;
                        }
                    });

        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
    public void validateAnswer(View view) {
//        Log.d(LOG_TAG, "prompted answer 1-> " + answerOne);
//        Log.d(LOG_TAG, "prompted answer 2-> " + answerTwo);
//        Log.d(LOG_TAG, "prompted answer 3-> " + answerThree);

        TextView attemptsView = findViewById(R.id.car_id);
        TextView scoreView = findViewById(R.id.score);

        try {
            int validatedAnswerOne = validateImages.validation(answerOne.toString(), populateData);
            int validatedAnswerTwo = validateImages.validation(answerTwo.toString(), populateData);
            int validatedAnswerThree = validateImages.validation(answerThree.toString(), populateData);

            if (attempts > 0) {
                if (answerOneHolder.isEnabled()) {
                    if (validatedAnswerOne == 0) {
                        answerOneHolder.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                        answerOneHolder.setEnabled(false);
                        score++;
                        Log.d(LOG_TAG, "correct answer 1 -> " + answerOne);
                    } else {
                        attempts = attempts - 1;
                        answerOneHolder.setBackground(getResources().getDrawable(R.drawable.wrong_answer));
                    }
                }

                if (answerTwoHolder.isEnabled()) {
                    if (validatedAnswerTwo == 1) {
                        answerTwoHolder.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                        answerTwoHolder.setEnabled(false);
                        score++;
                        Log.d(LOG_TAG, "correct answer 2 -> " + answerTwo);
                    } else {
                        attempts = attempts - 1;
                        answerTwoHolder.setBackground(getResources().getDrawable(R.drawable.wrong_answer));
                    }
                }

                if (answerThreeHolder.isEnabled()) {
                    if (validatedAnswerThree == 2) {
                        answerThreeHolder.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                        answerThreeHolder.setEnabled(false);
                        score++;
                        Log.d(LOG_TAG, "correct answer 3 -> " + answerThree);
                    } else {
                        attempts = attempts - 1;
                        answerThreeHolder.setBackground(getResources().getDrawable(R.drawable.wrong_answer));
                    }
                }
//                answerOneHolder.setText("");
//                answerTwoHolder.setText("");
//                answerThreeHolder.setText("");
            } else {
                Log.d(LOG_TAG, "attempts -> " + attempts);
            }

            attemptsView.setText(String.format("%02d", attempts));
            scoreView.setText(String.format("%02d", score));

            if (validatedAnswerOne == 0 & validatedAnswerTwo == 1 & validatedAnswerThree == 2) {
                Log.d(LOG_TAG, "all correct");
                styles.correctAnswer(String.format("%02d", attempts) + "/03");
            } else {
                Log.d(LOG_TAG, "at least one incorrect");
                styles.wrongAnswer(String.format("%02d", attempts) + "/03");
            }

//                validateImages.validation(userInputs, populateData, styles);

        } catch (Exception e) {
//            e.printStackTrace();

            Snackbar snackbar = Snackbar.make(view, "Enter all car makes to Proceed!", Snackbar.LENGTH_LONG);
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

    public void getInput() {

    }
}