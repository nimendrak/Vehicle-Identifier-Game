package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nimendra.utils.ImageLoader;

public class CarImageActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = CarMakeActivity.class.getSimpleName();

    private ImageView randomImageOne;
    private ImageView randomImageTwo;
    private ImageView randomImageThree;

    private TextView randomCarMake;

    private Button nextBtn;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        imageLoader = new ImageLoader(this);

        randomImageOne = findViewById(R.id.car_img1);
        randomImageTwo = findViewById(R.id.car_img2);
        randomImageThree = findViewById(R.id.car_img3);

        randomCarMake = findViewById(R.id.random_car_make);

        nextBtn = findViewById(R.id.next_btn);


    }


}