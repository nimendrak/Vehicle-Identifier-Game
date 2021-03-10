package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.nimendra.utils.Timer;
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
    private Timer timer;

    private EditText answerOneHolder;
    private EditText answerTwoHolder;
    private EditText answerThreeHolder;

    private Editable[] answerOne;
    private Editable[] answerTwo;
    private Editable[] answerThree;

    private TextView attemptsCount;

    private InputMethodManager imm;

    private int correctAns = 0;
    private int attempts = 3;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        TextView timerTextView = findViewById(R.id.timer);

        timer = new Timer(timerTextView);
        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(AdvancedActivity.this, this, imageLoader);
        styles = new Styles(AdvancedActivity.this, this, timer);
        populateData = new PopulateData(this, imageLoader, styles);

        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        answerOneHolder = findViewById(R.id.answer1_input);
        answerOne = getInput(answerOneHolder);

        answerTwoHolder = findViewById(R.id.answer2_input);
        answerTwo = getInput(answerTwoHolder);

        answerThreeHolder = findViewById(R.id.answer3_input);
        answerThree = getInput(answerThreeHolder);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
    public void validateAnswer(View view) {
        attemptsCount = findViewById(R.id.car_id);
        TextView scoreView = findViewById(R.id.score);

        // delete
        View separatorImgOne = findViewById(R.id.separator_img1);
        View separatorImgTwo = findViewById(R.id.separator_img2);
        View separatorImgThree = findViewById(R.id.separator_img3);

        try {
            Log.d(LOG_TAG, "input 1 -> " + answerOne[0].toString());
            Log.d(LOG_TAG, "input 2 -> " + answerOne[0].toString());
            Log.d(LOG_TAG, "input 3 -> " + answerOne[0].toString());

            int validatedAnswerOne = validateImages.validation(answerOne[0].toString(), populateData);
            int validatedAnswerTwo = validateImages.validation(answerTwo[0].toString(), populateData);
            int validatedAnswerThree = validateImages.validation(answerThree[0].toString(), populateData);

            if (attempts > 0) {
                if (answerOneHolder.isEnabled()) {
                    if (validatedAnswerOne == 0) {
                        correctAnswer(answerOneHolder, separatorImgOne);
                    } else {
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
                Log.d(LOG_TAG, "attempts -> " + attempts);
                Log.d(LOG_TAG, "correctAns -> " + correctAns);
                styles.wrongAnswer(String.format("%02d", (3 - correctAns)) + "/03");

                Log.d(LOG_TAG, "0 -> " + validatedAnswerOne);
                Log.d(LOG_TAG, "1 -> " + validatedAnswerTwo);
                Log.d(LOG_TAG, "2 -> " + validatedAnswerThree);

                if (validatedAnswerOne != 0) {
                    styles.setCorrectAnswerImgOne(validateImages, populateData);
                }
                if (validatedAnswerTwo != 1) {
                    styles.setCorrectAnswerImgTwo(validateImages, populateData);
                }
                if (validatedAnswerThree != 2) {
                    styles.setCorrectAnswerImgThree(validateImages, populateData);
                }
                nextAction();
            }

            attemptsCount.setText(String.format("%02d", attempts));
            scoreView.setText(String.format("%02d", score));

        } catch (Exception e) {
            e.printStackTrace();

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

    public void correctAnswer(EditText holder, View separator) {
        holder.setBackgroundResource(R.drawable.correct_answer);
        holder.setEnabled(false);
        correctAns++;
        score++;
        separator.setVisibility(View.INVISIBLE);
    }

    public void wrongAnswer(EditText holder, View separator) {
        holder.setBackgroundResource(R.drawable.wrong_answer);
        separator.setBackgroundColor(Color.parseColor("#FFFF615B"));
        separator.setVisibility(View.VISIBLE);
    }

    public void nextAction() {
        Button nextBtn = findViewById(R.id.next_btn);

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                answerOne[0].clear();
                answerTwo[0].clear();
                answerThree[0].clear();

                attempts = 3;
                correctAns = 0;
                styles.resetAnswer();
                populateData.setImagesTaskFour();
                attemptsCount.setText(String.format("%02d", attempts));
            }
        });
    }

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

}