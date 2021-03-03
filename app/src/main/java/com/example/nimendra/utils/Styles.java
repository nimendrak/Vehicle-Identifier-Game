package com.example.nimendra.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nimendra.R;

public class Styles {

    private static final String LOG_TAG = PopulateData.class.getSimpleName();

    // CarMakeActivity
    private ImageView carMakeLogo;
    private ImageView carImage;

    // CarImageActivity
    private ImageView randomImageOne;
    private ImageView randomImageTwo;
    private ImageView randomImageThree;
    private TextView randomCarMake;

    private TextView carMake;
    private TextView answer;
    private TextView carMakeLabel;
    private TextView carId;

    private ImageView gif;
    private View separator;

    private Button nextBtn;

    private ImageLoader imageLoader;
    private ValidateImages validateImages;

    private Context context;
    private Activity activity;

    public Styles(Activity activity, Context context, ImageLoader imageLoader, ValidateImages validateImages) {
        this.imageLoader = imageLoader;
        this.validateImages = validateImages;
        this.context = context;
        this.activity = activity;

        answer = activity.findViewById(R.id.answer);
        gif = activity.findViewById(R.id.correct_answer_gif);
        carMakeLabel = activity.findViewById(R.id.correct_car_label);

        separator = activity.findViewById(R.id.separator);
        carMake = activity.findViewById(R.id.correct_car);

        if (context.getClass().getSimpleName().equals("CarMakeActivity")) {
            this.context = context;
            this.activity = activity;
            this.imageLoader = imageLoader;
            this.validateImages = validateImages;

            carMakeLogo = activity.findViewById(R.id.car_logo);
            carImage = activity.findViewById(R.id.car_image);
            nextBtn = activity.findViewById(R.id.next_btn);
            carId = activity.findViewById(R.id.car_id);

        }
        if (context.getClass().getSimpleName().equals("CarImageActivity")) {
            this.context = context;
            this.activity = activity;
            this.imageLoader = imageLoader;
            this.validateImages = validateImages;

            nextBtn = activity.findViewById(R.id.next_btn);
            randomImageOne = activity.findViewById(R.id.car_img1);
            randomImageTwo = activity.findViewById(R.id.car_img2);
            randomImageThree = activity.findViewById(R.id.car_img3);

            randomCarMake = activity.findViewById(R.id.random_car_make);
        }
    }

    public void wrongAnswer(String selectedCar) {
        answer.setText(R.string.textView_wrong_text);
        answer.setTextColor(Color.parseColor("#ff0024"));
        answer.setVisibility(View.VISIBLE);

        separator.setVisibility(View.VISIBLE);

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

        carMake.setText(selectedCar);
        carMake.setVisibility(View.VISIBLE);

        gif.setImageResource(R.drawable.correct_gif);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
    }

    public void correctAnswer(String selectedCar, Integer imageHolder) {
        correctAnswer(selectedCar);
        Log.d(LOG_TAG, "Correct Answer -> " + imageHolder);

        switch (imageHolder) {
            case R.id.car_img1:
                randomImageOne.setBackgroundColor(Color.parseColor("#289995"));
                carMake.setText(R.string.textView_image1);
                break;
            case R.id.car_img2:
                randomImageTwo.setBackgroundColor(Color.parseColor("#289995"));
                carMake.setText(R.string.textView_image2);
                break;
            case R.id.car_img3:
                randomImageThree.setBackgroundColor(Color.parseColor("#289995"));
                carMake.setText(R.string.textView_image3);
                break;
        }
    }

    public void wrongAnswer(String selectedCar, Integer imageHolder) {
        Log.d(LOG_TAG, "Wrong Answer -> " + imageHolder);
        wrongAnswer(selectedCar);

        switch (imageHolder) {
            case R.id.car_img1:
                System.out.println("came here");
                randomImageOne.setBackgroundColor(Color.parseColor("#ff9995"));
                carMake.setText(R.string.textView_image1);
                break;
            case R.id.car_img2:
                randomImageTwo.setBackgroundColor(Color.parseColor("#ff9995"));
                carMake.setText(R.string.textView_image2);
                break;
            case R.id.car_img3:
                randomImageThree.setBackgroundColor(Color.parseColor("#ff9995"));
                carMake.setText(R.string.textView_image3);
                break;
        }
    }

    public void resetAnswer() {
        if (context.getClass().getSimpleName().equals("CarMakeActivity")) {
            gif.setVisibility(View.INVISIBLE);
            carMake.setVisibility(View.INVISIBLE);
            carMakeLabel.setVisibility(View.INVISIBLE);
            answer.setVisibility(View.INVISIBLE);
            separator.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.INVISIBLE);
        }

        if (context.getClass().getSimpleName().equals("CarImageActivity")) {
            gif.setVisibility(View.INVISIBLE);
            carMake.setVisibility(View.INVISIBLE);
            carMakeLabel.setVisibility(View.INVISIBLE);
            answer.setVisibility(View.INVISIBLE);
            separator.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.INVISIBLE);

            randomImageOne.setBackgroundColor(Color.parseColor("#289995"));
            randomImageTwo.setBackgroundColor(Color.parseColor("#289995"));
            randomImageThree.setBackgroundColor(Color.parseColor("#289995"));
        }
    }

    public ImageView getCarMakeLogo() {
        return carMakeLogo;
    }

    public ImageView getCarImage() {
        return carImage;
    }

    public ImageView getRandomImageOne() {
        return randomImageOne;
    }

    public ImageView getRandomImageTwo() {
        return randomImageTwo;
    }

    public ImageView getRandomImageThree() {
        return randomImageThree;
    }

    public TextView getRandomCarMake() {
        return randomCarMake;
    }

    public TextView getCarId() {
        return carId;
    }
}
