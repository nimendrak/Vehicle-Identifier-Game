package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private static Field[] carImagesArray = R.drawable.class.getDeclaredFields();
    private static Field[] carLogosArray = R.drawable.class.getDeclaredFields();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        carMake = findViewById(R.id.correct_car);
        carMakeLogo = findViewById(R.id.car_logo);
        carImage = findViewById(R.id.car_image);

        answer = findViewById(R.id.answer);
        gif = findViewById(R.id.correct_answer_gif);
        carMakeLabel = findViewById(R.id.correct_car_label);

        separator = findViewById(R.id.separator);

        gif.setVisibility(View.INVISIBLE);
        carMake.setVisibility(View.INVISIBLE);
        carMakeLabel.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);

        // create the spinner
        Spinner spinner = findViewById(R.id.car_make_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array,
                R.layout.spinner_checked_layout);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Log.d(LOG_TAG, "Selected item - " + item);

        if (parent.getItemAtPosition(position).equals("Select a Manufacturer")) {
//            dialog box
        } else {
            if (parent.getItemAtPosition(position).equals("Audi")) {
                correctAnswer(item);
            } else {
                wrongAnswer(item);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void identifyAction() {
        Random random = new Random();
    }


    public void wrongAnswer(String selectedCar) {
        answer.setText(R.string.textView_wrong_text);
        separator.setVisibility(View.VISIBLE);
        answer.setTextColor(Color.parseColor("#FF0000"));
        carMake.setVisibility(View.VISIBLE);
        carMake.setText(selectedCar);

        gif.setImageResource(R.drawable.wrong_gif);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);

        carImage.getId();
        System.out.println(carImage.getId());

        selectedCar = selectedCar.toLowerCase();
        for (Field f : carImagesArray) {
            try {
                if (f.getName().contains(selectedCar))
                    System.out.println("R.drawable." + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void correctAnswer(String selectedCar) {
        answer.setText(R.string.textView_answer_text);
        separator.setVisibility(View.VISIBLE);
        carMake.setVisibility(View.VISIBLE);
        carMake.setText(selectedCar);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);

        carImage.getId();
        System.out.println(carImage.getId());

        selectedCar = selectedCar.toLowerCase();
        for (Field f : carImagesArray) {
            try {
                if (f.getName().contains(selectedCar))
                    System.out.println("R.drawable." + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}