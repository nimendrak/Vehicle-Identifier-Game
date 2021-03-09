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

import java.util.ArrayList;
import java.util.List;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void validateAnswer(View view) {
        Log.d(LOG_TAG, "prompted answer 1-> " + answerOne);
        Log.d(LOG_TAG, "prompted answer 2-> " + answerTwo);
        Log.d(LOG_TAG, "prompted answer 3-> " + answerThree);

        List<String> userInputs = new ArrayList<>();
        userInputs.add(answerOne.toString());
        userInputs.add(answerTwo.toString());
        userInputs.add(answerThree.toString());

        validateImages.validation(userInputs, populateData);

        answerOneHolder.setBackground(getResources().getDrawable(R.drawable.correct_answer));
        answerTwoHolder.setBackground(getResources().getDrawable(R.drawable.correct_answer));
        answerThreeHolder.setBackground(getResources().getDrawable(R.drawable.correct_answer));
    }

    public void getInput() {

    }
}