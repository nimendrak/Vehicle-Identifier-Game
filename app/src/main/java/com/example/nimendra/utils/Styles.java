package com.example.nimendra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.nimendra.R;

public class Styles {

    private static final String LOG_TAG = Styles.class.getSimpleName();

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

    private CardView answerPrompter;

    private Context context;

    private String activityName;

    public Styles(Activity activity, Context context) {
        this.context = context;
        activityName = context.getClass().getSimpleName();

        answerPrompter = activity.findViewById(R.id.answer_prompter);

        answer = activity.findViewById(R.id.answer);
        gif = activity.findViewById(R.id.correct_answer_gif);
        carMakeLabel = activity.findViewById(R.id.correct_car_label);

        separator = activity.findViewById(R.id.separator);
        carMake = activity.findViewById(R.id.correct_car);

        if (activityName.equals("CarMakeActivity")) {
            this.context = context;

            carMakeLogo = activity.findViewById(R.id.car_logo);
            carImage = activity.findViewById(R.id.car_image);
            nextBtn = activity.findViewById(R.id.next_btn);
            carId = activity.findViewById(R.id.car_id);

        }
        if (activityName.equals("CarImageActivity")) {
            this.context = context;

            nextBtn = activity.findViewById(R.id.next_btn);
            randomImageOne = activity.findViewById(R.id.car_img1);
            randomImageTwo = activity.findViewById(R.id.car_img2);
            randomImageThree = activity.findViewById(R.id.car_img3);

            randomCarMake = activity.findViewById(R.id.random_car_make);
        }
        if (activityName.equals("CarHintActivity")) {
            this.context = context;

            carMakeLogo = activity.findViewById(R.id.car_logo);
            carImage = activity.findViewById(R.id.car_image);
            nextBtn = activity.findViewById(R.id.next_btn);
            carId = activity.findViewById(R.id.car_id);

            randomCarMake = activity.findViewById(R.id.random_car_make);
        }
    }

    public void wrongAnswer(String selectedCar) {
        answer.setText(R.string.textView_wrong_text);
        carMake.setText(selectedCar);
        answer.setTextColor(Color.parseColor("#ff0024"));
        gif.setImageResource(R.drawable.wrong_gif);

        answerPrompter.setVisibility(View.VISIBLE);
    }

    public void correctAnswer(String selectedCar) {
        answer.setText(R.string.textView_correct_text);
        answer.setTextColor(Color.parseColor("#289995"));
        carMake.setText(selectedCar);
        gif.setImageResource(R.drawable.correct_gif);

        answerPrompter.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    public void wrongAnswer(String selectedCar, Integer imageHolder) {
        Log.d(LOG_TAG, "Wrong Answer -> " + imageHolder);
        wrongAnswer(selectedCar);

        switch (imageHolder) {
            case R.id.car_img1:
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

    @SuppressLint("NonConstantResourceId")
    public void markCorrectAnswer(Integer imageHolder) {
        Log.d(LOG_TAG, "Mrk Correct Answer -> " + imageHolder);

        switch (imageHolder) {
            case R.id.car_img1:
                randomImageOne.setBackgroundColor(Color.parseColor("#FFC107"));
                carMake.setText(R.string.textView_image1);
                break;
            case R.id.car_img2:
                randomImageTwo.setBackgroundColor(Color.parseColor("#FFC107"));
                carMake.setText(R.string.textView_image2);
                break;
            case R.id.car_img3:
                randomImageThree.setBackgroundColor(Color.parseColor("#FFC107"));
                carMake.setText(R.string.textView_image3);
                break;
        }

    }

    public void resetAnswer() {
        if (activityName.equals("CarMakeActivity") |
                activityName.equals("CarHintActivity")) {
            answerPrompter.setVisibility(View.INVISIBLE);

            nextBtn.setVisibility(View.INVISIBLE);
        }

        if (activityName.equals("CarImageActivity")) {
            answerPrompter.setVisibility(View.INVISIBLE);

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
