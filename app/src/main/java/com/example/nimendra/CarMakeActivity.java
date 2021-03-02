package com.example.nimendra;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.ValidateImages;

public class CarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Class name for Log tag
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    // TextView for correct car make
    private TextView carMake;
    // ImageView for correct car make
    private ImageView carImage;
    private TextView answer;

    private ImageView gif;
    private ImageView carMakeLogo;
    private TextView carMakeLabel;
    private View separator;

    private Button identifyBtn;

    private TextView carId;

    private ImageLoader imageLoader;
    private ValidateImages validateImages;

    private Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        carMake = (TextView) findViewById(R.id.correct_car);
        carMakeLogo = (ImageView) findViewById(R.id.car_logo);
        carImage = (ImageView) findViewById(R.id.car_image);
        carId = (TextView) findViewById(R.id.car_id);

        answer = (TextView) findViewById(R.id.answer);
        gif = (ImageView) findViewById(R.id.correct_answer_gif);
        carMakeLabel = (TextView) findViewById(R.id.correct_car_label);

        separator = (View) findViewById(R.id.separator);

        identifyBtn = (Button) findViewById(R.id.identify_btn);

        resetAnswer();

        imageLoader = new ImageLoader(this);

        // create the spinner
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
                        resetAnswer();
                    } else {
                        if (imageLoader.getCarImagesArray().size() > 0) {
                            switch (item) {
                                case "Audi":
                                case "Bugatti":
                                case "BMW":
                                case "Ferrari":
                                case "Koenigsegg":
                                case "Mercedes-Benz":
                                case "Porsche":
                                case "Tesla":
                                    validateAnswer(item);
                                    identifyBtn.setText(R.string.button_identify_text_2);
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

    public void resetAnswer() {
        gif.setVisibility(View.INVISIBLE);
        carMake.setVisibility(View.INVISIBLE);
        carMakeLabel.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);
    }

    public void wrongAnswer(String selectedCar) {
        answer.setText(R.string.textView_wrong_text);
        answer.setTextColor(Color.parseColor("#ff0024"));
        answer.setVisibility(View.VISIBLE);

        separator.setVisibility(View.VISIBLE);

        carId.setText(String.valueOf(imageLoader.getCarImagesArray().size()));

        carMake.setText(selectedCar);
        carMake.setVisibility(View.VISIBLE);

        gif.setImageResource(R.drawable.wrong_gif);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
    }

    public void correctAnswer(String selectedCar) {
        answer.setText(R.string.textView_correct_text);
        answer.setTextColor(Color.parseColor("#289995"));
        answer.setVisibility(View.VISIBLE);

        separator.setVisibility(View.VISIBLE);

        carId.setText(String.valueOf(imageLoader.getCarImagesArray().size()));

        carMake.setText(selectedCar);
        carMake.setVisibility(View.VISIBLE);

        gif.setImageResource(R.drawable.correct_gif);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
    }

    public void validateAnswer(String selectedCar) {
        validateImages = new ValidateImages(this, selectedCar, imageLoader);

        if (validateImages.validation()) {
            correctAnswer(selectedCar);

            identifyBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    spinner.setEnabled(true);
                    spinner.setSelection(0);
                    spinner.setBackgroundResource(R.drawable.spinner_color_layout);
                    carImage.setImageResource(imageLoader.getCarImagesArray().get(validateImages.getRandom()));
                }
            });

        } else {
            wrongAnswer(selectedCar);
        }
    }
}