package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.example.nimendra.utils.ValidateImages;

public class CarHintActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarHintActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private ImageLoader imageLoader;
    private PopulateData populateData;
    private Styles styles;

    private EditText inputChar;
    private Editable inputCharStr;

    private int attempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(CarHintActivity.this, this, imageLoader);
        styles = new Styles(CarHintActivity.this, this);
        populateData = new PopulateData(this, imageLoader, styles);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputChar = findViewById(R.id.input_char);
        if (inputChar != null) {
            inputChar.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                inputCharStr = inputChar.getText();
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                handled = true;
                            }
                            return handled;
                        }
                    });

        }
    }

    public void submitAnswer(View view) {
        String correctAnswer = validateImages.getCorrectCarMakeTaskThree();

        if (attempts > 0) {
            Log.d(LOG_TAG, "attempts -> " + attempts);
            if (validateImages.validation(inputCharStr)) {
                if (correctAnswer != null)
                    styles.correctAnswer(correctAnswer);
            } else {
                attempts = attempts - 1;
            }
        } else {
            styles.wrongAnswer(validateImages.getCorrectCarMake());
        }
        Log.d(LOG_TAG, "correct car -> " + correctAnswer);
        inputChar.setText("");



//        Button nextBtn = findViewById(R.id.next_btn);
//        nextBtn.setVisibility(View.VISIBLE);
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                populateData.setImagesTaskTwo();
//                styles.resetAnswer();
//            }
//        });
    }
}