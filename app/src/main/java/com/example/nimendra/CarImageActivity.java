package com.example.nimendra;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.ValidateImages;

public class CarImageActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private ImageLoader imageLoader;
    private PopulateData populateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(CarImageActivity.this, this, imageLoader);
        populateData = new PopulateData(CarImageActivity.this, this, imageLoader, validateImages);

        Button identifyBtn = findViewById(R.id.identify_btn);
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "in validate() -> nextBtn");
                populateData.setImagesTaskThree();
                populateData.resetAnswer();
            }
        });
    }

    public void validateAnswer(View view) {
        TextView randomCarMake = populateData.getRandomCarMake();
        String randomCarMakeStr = (String) randomCarMake.getText();

        int id = view.getId();
        switch (id) {
            case R.id.car_img1:
            case R.id.car_img2:
            case R.id.car_img3:
                if (validateImages.validation(id, randomCarMakeStr, populateData)) {
                    populateData.correctAnswer(randomCarMakeStr, id);
                    Log.d(LOG_TAG, "in validate() -> correct");
                } else {
                    populateData.wrongAnswer(randomCarMakeStr, id);
                    Log.d(LOG_TAG, "in validate() -> wrong");
                }
                break;
        }
    }
}