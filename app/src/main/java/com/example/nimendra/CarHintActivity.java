package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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

    private Button nextBtn;
    private EditText inputChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(CarHintActivity.this, this, imageLoader);
        styles = new Styles(CarHintActivity.this, this);

        populateData = new PopulateData(this, imageLoader, styles);

        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "submit btn -> on click");
                populateData.setImagesTaskTwo();
                styles.resetAnswer();
            }
        });

        final EditText inputChar = findViewById(R.id.input_char);
        if (inputChar != null) {
            inputChar.setOnEditorActionListener
                    (new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            boolean handled = false;
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                Log.d(LOG_TAG, "input char should come here");
                                handled = true;
                            }
                            return handled;
                        }
                        // If view is found, set the listener for editText.
                    });

        }
    }

    public void submitAnswer(View view) {
        TextView randomCarMake = styles.getRandomCarMake();
        String randomCarMakeStr = (String) randomCarMake.getText();

        String inputCharStr = inputChar.getText().toString();

        Log.d(LOG_TAG, "input char -> " + inputCharStr);

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateData.setImagesTaskTwo();
                styles.resetAnswer();
            }
        });

    }
}