package com.example.nimendra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    // AdvancedActivity
    private TextView imageOneAns;
    private TextView imageTwoAns;
    private TextView imageThreeAns;

    private TextView carMake;
    private TextView answer;
    private TextView carMakeLabel;
    private TextView carId;

    private TextView correctAnswerCount;

    private EditText answerOneHolder;
    private EditText answerTwoHolder;
    private EditText answerThreeHolder;

    private ImageView gif;

    private View separator;
    private View separatorImgOne;
    private View separatorImgTwo;
    private View separatorImgThree;

    private Button nextBtn;

    private CardView answerPrompter;

    private Context context;

    private String activityName;

    private Activity activity;

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
        if (activityName.equals("AdvancedActivity")) {
            this.context = context;

            correctAnswerCount = activity.findViewById(R.id.correct_answers_count);

            nextBtn = activity.findViewById(R.id.next_btn);

            separatorImgOne = activity.findViewById(R.id.separator_img1);
            separatorImgTwo = activity.findViewById(R.id.separator_img2);
            separatorImgThree = activity.findViewById(R.id.separator_img3);

            randomImageOne = activity.findViewById(R.id.car_img1);
            randomImageTwo = activity.findViewById(R.id.car_img2);
            randomImageThree = activity.findViewById(R.id.car_img3);

            imageOneAns = activity.findViewById(R.id.img1_index);
            imageTwoAns = activity.findViewById(R.id.img2_index);
            imageThreeAns = activity.findViewById(R.id.img3_index);

            answerOneHolder = activity.findViewById(R.id.answer1_input);
            answerTwoHolder = activity.findViewById(R.id.answer2_input);
            answerThreeHolder = activity.findViewById(R.id.answer3_input);
        }
    }

    public void wrongAnswer(String selectedCar) {
        answer.setText(R.string.textView_wrong_text);

        if (activityName.equals("AdvancedActivity")) {
            correctAnswerCount.setText(selectedCar);
        } else {
            carMake.setText(selectedCar);
        }

        answer.setTextColor(Color.parseColor("#ff0024"));
        gif.setImageResource(R.drawable.wrong_gif);

        answerPrompter.setVisibility(View.VISIBLE);
    }

    public void correctAnswer(String selectedCar) {
        answer.setText(R.string.textView_correct_text);

        if (activityName.equals("AdvancedActivity")) {
            correctAnswerCount.setText(selectedCar);
        } else {
            carMake.setText(selectedCar);
        }

        answer.setTextColor(Color.parseColor("#289995"));
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void resetAnswer() {
        if (activityName.equals("CarMakeActivity") |
                activityName.equals("CarHintActivity")) {

            nextBtn.setVisibility(View.INVISIBLE);
            answerPrompter.setVisibility(View.INVISIBLE);
        }

        if (activityName.equals("CarImageActivity")) {

            nextBtn.setVisibility(View.INVISIBLE);
            answerPrompter.setVisibility(View.INVISIBLE);

            randomImageOne.setBackgroundColor(Color.parseColor("#289995"));
            randomImageTwo.setBackgroundColor(Color.parseColor("#289995"));
            randomImageThree.setBackgroundColor(Color.parseColor("#289995"));
        }

        if (activityName.equals("AdvancedActivity")) {

            nextBtn.setVisibility(View.INVISIBLE);
            answerPrompter.setVisibility(View.INVISIBLE);

            imageOneAns.setVisibility(View.INVISIBLE);
            imageTwoAns.setVisibility(View.INVISIBLE);
            imageThreeAns.setVisibility(View.INVISIBLE);

            separatorImgOne.setVisibility(View.VISIBLE);
            separatorImgTwo.setVisibility(View.VISIBLE);
            separatorImgThree.setVisibility(View.VISIBLE);

            separatorImgOne.setBackgroundColor(Color.parseColor("#232C32"));
            separatorImgTwo.setBackgroundColor(Color.parseColor("#232C32"));
            separatorImgThree.setBackgroundColor(Color.parseColor("#232C32"));

            answerOneHolder.setText("");
            answerTwoHolder.setText("");
            answerThreeHolder.setText("");

            answerOneHolder.setEnabled(true);
            answerTwoHolder.setEnabled(true);
            answerThreeHolder.setEnabled(true);

            answerOneHolder.setBackgroundResource(0);
            answerTwoHolder.setBackgroundResource(0);
            answerThreeHolder.setBackgroundResource(0);

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

    public TextView getImageOneAns() {
        return imageOneAns;
    }

    public TextView getImageTwoAns() {
        return imageTwoAns;
    }

    public TextView getImageThreeAns() {
        return imageThreeAns;
    }
}
