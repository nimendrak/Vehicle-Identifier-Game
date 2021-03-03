package com.example.nimendra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nimendra.R;

public class CommonComp {
    private TextView carMake;
    private TextView answer;
    private TextView carMakeLabel;
    private TextView carId;

    private ImageView gif;
    private ImageView carMakeLogo;
    private ImageView carImage;

    private View separator;

    private Button nextBtn;

    private ImageLoader imageLoader;
    private ValidateImages validateImages;

    private Context context;

    public CommonComp(Activity activity, Context context) {
        this.context = context;

        carMake = activity.findViewById(R.id.correct_car);
        carMakeLogo = activity.findViewById(R.id.car_logo);
        carImage = activity.findViewById(R.id.car_image);

        carId = activity.findViewById(R.id.car_id);

        answer = activity.findViewById(R.id.answer);
        gif = activity.findViewById(R.id.correct_answer_gif);
        carMakeLabel = activity.findViewById(R.id.correct_car_label);

        separator = activity.findViewById(R.id.separator);
        nextBtn = activity.findViewById(R.id.next_btn);

        imageLoader = new ImageLoader(context);
        validateImages = new ValidateImages(activity, context, imageLoader);
    }

    public void setImagesTaskOne() {
        // car
        Integer currentImageResource = imageLoader.getCarImagesArray().get(validateImages.getRandom());
        String currentImgResourceName = context.getResources().getResourceEntryName(currentImageResource);

        carImage.setImageResource(currentImageResource);
        carImage.setTag(currentImgResourceName);

        // car logo
        try {
            for (int i = 0; i < imageLoader.getCarLogosArray().size(); i++) {
                Integer currentLogoResource = imageLoader.getCarLogosArray().get(i);
                String currentLogoResourceName = context.getResources().getResourceEntryName(currentLogoResource);

                if (currentImgResourceName.contains(currentLogoResourceName)) {
                    carMakeLogo.setImageResource(currentLogoResource);
                    carMakeLogo.setTag(currentLogoResourceName);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImagesTaskThree() {

    }

    @SuppressLint("DefaultLocale")
    public void wrongAnswer(String selectedCar) {
        answer.setText(R.string.textView_wrong_text);
        answer.setTextColor(Color.parseColor("#ff0024"));
        answer.setVisibility(View.VISIBLE);

        separator.setVisibility(View.VISIBLE);

        int id = imageLoader.getCarImagesArray().size();
        carId.setText(String.format("%02d", id));

        carMake.setText(selectedCar);
        carMake.setVisibility(View.VISIBLE);

        gif.setImageResource(R.drawable.wrong_gif);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
    }

    @SuppressLint("DefaultLocale")
    public void correctAnswer(String selectedCar) {
        answer.setText(R.string.textView_correct_text);
        answer.setTextColor(Color.parseColor("#289995"));
        answer.setVisibility(View.VISIBLE);

        separator.setVisibility(View.VISIBLE);

        int id = imageLoader.getCarImagesArray().size();
        carId.setText(String.format("%02d", id));

        carMake.setText(selectedCar);
        carMake.setVisibility(View.VISIBLE);

        gif.setImageResource(R.drawable.correct_gif);
        gif.setVisibility(View.VISIBLE);

        carMakeLabel.setVisibility(View.VISIBLE);
    }

    public void resetAnswer() {
        gif.setVisibility(View.INVISIBLE);
        carMake.setVisibility(View.INVISIBLE);
        carMakeLabel.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
    }
}
