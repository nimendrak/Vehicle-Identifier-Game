package com.example.nimendra;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.Timer;
import com.example.nimendra.utils.ValidateImages;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.Styles;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Class name for Log tag
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;
    private Timer timer;

    private boolean isValidated;
    private boolean switchStats;
    private Spinner spinner = null;
    private TextView timerTextView;

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
        Log.d(LOG_TAG, "switch stats -> " + switchStats);

        // Countdown digits holder
        timerTextView = findViewById(R.id.timer);

        // Check whether switcher is on or off
        // And start the timer accordingly
        if (switchStats) {
            timer = new Timer(timerTextView);
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
                    String item = spinner.getSelectedItem().toString();

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
                                case "Bugatti":
                                case "BMW":
                                case "Ferrari":
                                case "Koenigsegg":
                                case "Mercedes":
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

        // If switchStats == true,
        // User has 20s to submit an answer
        // Otherwise it will automatically submit and,
        // Remove the current image from the image array
        handlerConfig("not selected");
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
            isValidated = true;
            Log.d(LOG_TAG, "in validateAnswer() -> correct");
        } else {
            // If answer is wrong or null -> answer prompter change as follows
            // With the correct answer
            // validateImages.getCorrectCarMakeTaskTwo() returns the correct answer of the task
            styles.wrongAnswer(validateImages.getCorrectCarMakeTaskTwo());
            Log.d(LOG_TAG, "in validateAnswer() -> wrong");
            isValidated = true;
        }

        // Either answer is wrong or correct
        // spinner will disable and change it color
        spinner.setEnabled(false);
        spinner.setBackgroundResource(R.drawable.spinner_color_layout_disbaled);

        // Also, if the switcher is on
        // Countdown will freeze as well
        if (switchStats) {
            Log.d(LOG_TAG, "time paused");
            timer.pauseTimer();
        }

        Button nextBtn = findViewById(R.id.next_btn);

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Once the next button is clicked,
                // Answer prompter will disappear
                styles.resetAnswer();

                // Set flag to false, before next validation
                isValidated = false;

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
                handlerConfig("not selected");
            }
        });
    }

    // If the switcher is on
    // handlerConfig lets countdown run for 20s
    // And automatically submit the current answer
    public void handlerConfig(final String selectedCar) {
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
                    if (!isValidated)
                        validateAnswer(selectedCar);
                        isValidated = true;
                }
            }, 10000);
            // If switcher is off, countdown text view will stay hidden
        } else {
            timerTextView.setVisibility(View.INVISIBLE);
        }
    }
}
