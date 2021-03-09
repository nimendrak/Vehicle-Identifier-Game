package com.example.nimendra;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.ValidateImages;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.Styles;

public class CarImageActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(CarImageActivity.this, this, imageLoader);
        styles = new Styles(CarImageActivity.this, this);
        populateData = new PopulateData( this, imageLoader, styles);

        Button identifyBtn = findViewById(R.id.identify_btn);
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateData.setImagesTaskThree();
                styles.resetAnswer();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void validateAnswer(View view) {
        TextView randomCarMake = styles.getRandomCarMake();
        String randomCarMakeStr = (String) randomCarMake.getText();

        int id = view.getId();
        switch (id) {
            case R.id.car_img1:
            case R.id.car_img2:
            case R.id.car_img3:
                if (validateImages.validation(id, randomCarMakeStr, populateData)) {
                    styles.correctAnswer(randomCarMakeStr);
                    styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                    Log.d(LOG_TAG, "in validate() -> correct");
                } else {
                    styles.wrongAnswer(randomCarMakeStr, id);
                    styles.markCorrectAnswer(validateImages.getCorrectCarMakeTaskTwo(populateData));
                    Log.d(LOG_TAG, "in validate() -> wrong");
                }
                break;
        }
    }
}