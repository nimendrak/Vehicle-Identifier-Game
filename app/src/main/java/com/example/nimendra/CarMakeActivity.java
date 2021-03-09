package com.example.nimendra;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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

    private Button nextBtn;
    private Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(CarMakeActivity.this, this, imageLoader);
        styles = new Styles(CarMakeActivity.this, this);
        populateData = new PopulateData(this, imageLoader, styles);

        Button identifyBtn = findViewById(R.id.identify_btn);
        nextBtn = findViewById(R.id.next_btn);

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
                    String item = spinner.getSelectedItem().toString();

                    if (item.equals("Select a Manufacturer")) {
                        // item returns array_car_makes[0] answer prompter will stay in its reset state
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
                                    spinner.setEnabled(false);
                                    spinner.setBackgroundResource(R.drawable.spinner_color_layout_disbaled);
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

    public void validateAnswer(String selectedCar) {
        if (validateImages.validation(selectedCar)) {
            styles.correctAnswer(validateImages.getCorrectCarMakeTaskTwo());
            Log.d(LOG_TAG, "in validate() -> correct");
        } else {
            styles.wrongAnswer(validateImages.getCorrectCarMakeTaskTwo());
            Log.d(LOG_TAG, "in validate() -> wrong");
        }

        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                spinner.setEnabled(true);

                populateData.setImagesTaskOne();

                spinner.setBackgroundResource(R.drawable.spinner_color_layout);
                styles.resetAnswer();
            }
        });
    }
}
