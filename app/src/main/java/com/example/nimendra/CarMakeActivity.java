package com.example.nimendra;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.ValidateImages;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.Styles;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class CarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Class name for Log tag
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;

    private boolean switchStats;
    private Spinner spinner = null;
    private TextView timerTextView;

    private static final long START_TIME_IN_MILLIS = 10000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer countDownTimer;

    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        // This class will load all the images into two different arrays
        // 1 -> car img arr, 2 -> car logo arr
        imageLoader = new ImageLoader(this);

        // This class will do the validations according to imageLoader
        validateImages = new ValidateImages(CarMakeActivity.this, this, imageLoader);

        // This class will do the necessary styling to the current activity
        styles = new Styles(CarMakeActivity.this, this);

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

        Button identifyBtn = findViewById(R.id.identify_btn);

        // Create the spinner
        spinner = findViewById(R.id.car_make_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_car_makes,
                R.layout.spinner_checked_layout);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner != null) {
                    // Item is the selected car make from the spinner
                    item = spinner.getSelectedItem().toString();

                    if (item.equals("Select a Manufacturer")) {
                        // Item returns array_car_makes[0] answer prompter will stay in its reset state
                        styles.resetAnswer();

                        // And shows a SnackBar to the consumer with a proper message
                        Snackbar snackbar = Snackbar.make(v, "Please Select a Car Make!", Snackbar.LENGTH_LONG);
                        snackbar.setDuration(2500);
                        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                        snackbar.show();
                    } else {
                        if (imageLoader.getCarImagesArray().size() > 0) {
                            switch (item) {
                                case "Audi":
                                case "BMW":
                                case "Jaguar":
                                case "Ferrari":
                                case "Lamborghini":
                                case "Mercedes":
                                case "Mitsubishi":
                                case "Porsche":
                                case "Tesla":
                                    validateAnswer(item);
                                    break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void validateAnswer(final String selectedCar) {
        // validateImages.validation(selectedCar) returns a boolean value
        // true -> answer is correct
        // false -> either answer is wrong or select nothing from the spinner (null)
        if (validateImages.validation(selectedCar)) {
            // If answer is correct -> answer prompter change as follows
            // With the correct answer
            // validateImages.getCorrectCarMakeTaskTwo() returns the correct answer of the task
            styles.correctAnswer(validateImages.getCorrectCarMakeTaskTwo());
        } else {
            // If answer is wrong or null -> answer prompter change as follows
            // With the correct answer
            // validateImages.getCorrectCarMakeTaskTwo() returns the correct answer of the task
            styles.wrongAnswer(validateImages.getCorrectCarMakeTaskTwo());
        }

        // Also, if the switcher is on
        // Countdown will freeze as well
        if (switchStats) {
            pauseTimer();
        }

        // Either answer is wrong or correct
        // spinner will disable and change it color
        spinner.setEnabled(false);
        spinner.setBackgroundResource(R.drawable.spinner_color_layout_disbaled);

        Button nextBtn = findViewById(R.id.next_btn);

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Once the next button is clicked,
                // Answer prompter will disappear
                styles.resetAnswer();

                // And set it value to the first index
                spinner.setSelection(0);
                // Enable it again to select a car make
                spinner.setEnabled(true);

                // Randomly select an image and
                // populate image holder again without previous image
                populateData.setImagesTaskOne();
                // Set spinner's default background
                spinner.setBackgroundResource(R.drawable.spinner_color_layout);

                // If user does nothing for next 20s,
                // Program will automatically submit an answer
                if (switchStats) {
                    resetTimer();
                    startTimer();
                }
            }
        });
    }

    // CountDownTimer start when the switchStats == true
    // onFinish() it automatically calls the validating method
    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                validateAnswer(item);
            }
        }.start();
    }

    public void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
    }

    public void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        if (seconds <= 5) {
            timerTextView.setTextColor(Color.parseColor("#ff0024"));
        } else {
            timerTextView.setTextColor(Color.WHITE);
        }
        timerTextView.setText(timeLeftFormatted);
    }
}
