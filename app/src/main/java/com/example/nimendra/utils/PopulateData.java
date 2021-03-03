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

import com.example.nimendra.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PopulateData {

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

    private List<Integer> imgArr;
    private List<Integer> imageHolders;
    private List<Integer> logoArr;

    public PopulateData(Activity activity, Context context, ImageLoader imageLoader, ValidateImages validateImages) {
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
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();

            carMakeLogo = activity.findViewById(R.id.car_logo);
            carImage = activity.findViewById(R.id.car_image);
            nextBtn = activity.findViewById(R.id.next_btn);
            carId = activity.findViewById(R.id.car_id);

            setImagesTaskOne();
        }
        if (context.getClass().getSimpleName().equals("CarImageActivity")) {
            this.context = context;
            this.activity = activity;
            this.imageLoader = imageLoader;
            this.validateImages = validateImages;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();

            nextBtn = activity.findViewById(R.id.next_btn);
            randomImageOne = activity.findViewById(R.id.car_img1);
            randomImageTwo = activity.findViewById(R.id.car_img2);
            randomImageThree = activity.findViewById(R.id.car_img3);

            randomCarMake = activity.findViewById(R.id.random_car_make);

            setImagesTaskThree();
        }
        resetAnswer();
    }

    @SuppressLint("DefaultLocale")
    public void setImagesTaskOne() {
        System.out.println(imgArr);
        // car
        Integer currentImageResource = imgArr.get(getRandomIndex(imgArr));
        String currentImgResourceName = context.getResources().getResourceEntryName(currentImageResource);

        carImage.setImageResource(currentImageResource);
        carImage.setTag(currentImgResourceName);

        // car logo
        try {
            for (int i = 0; i < logoArr.size(); i++) {
                Integer currentLogoResource = logoArr.get(i);
                String currentLogoResourceName = context.getResources().getResourceEntryName(currentLogoResource);

                if (currentImgResourceName.contains(currentLogoResourceName)) {
                    carMakeLogo.setImageResource(currentLogoResource);
                    carMakeLogo.setTag(currentLogoResourceName);

                    int id = imageLoader.getCarImagesArray().size();
                    carId.setText(String.format("%02d", id));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImagesTaskTwo() {

    }

    public void setImagesTaskThree() {

        Integer randomImgOneResource, randomImgTwoResource, randomImgThreeResource;
        String randomImgOneResourceName, randomImgTwoResourceName, randomImgThreeResourceName;
        String[] carOne, carTwo, carThree;

        boolean isCorrect = true;

        do {
            randomImgOneResource = imgArr.get(getRandomIndex(imgArr));
            randomImgOneResourceName = context.getResources().getResourceEntryName(randomImgOneResource);

            carOne = randomImgOneResourceName.split("[_]");

            randomImgTwoResource = imgArr.get(getRandomIndex(imgArr));
            randomImgTwoResourceName = context.getResources().getResourceEntryName(randomImgTwoResource);

            carTwo = randomImgTwoResourceName.split("[_]");

            randomImgThreeResource = imgArr.get(getRandomIndex(imgArr));
            randomImgThreeResourceName = context.getResources().getResourceEntryName(randomImgThreeResource);

            carThree = randomImgThreeResourceName.split("[_]");

            if (!carOne[1].equals(carTwo[1]) & !carTwo[1].equals(carThree[1]) & !carOne[1].equals(carThree[1])) {
                if (!carOne[2].equals(carTwo[2]) & !carTwo[2].equals(carThree[2]) & !carOne[2].equals(carThree[2])) {
                    isCorrect = false;
                }
            }
        } while (isCorrect);

        randomImageOne.setImageResource(randomImgOneResource);
        randomImageOne.setTag(randomImgOneResourceName);

        randomImageTwo.setImageResource(randomImgTwoResource);
        randomImageTwo.setTag(randomImgTwoResourceName);

        randomImageThree.setImageResource(randomImgThreeResource);
        randomImageThree.setTag(randomImgThreeResourceName);

        imageHolders = new ArrayList<>();
        imageHolders.add(randomImgOneResource);
        imageHolders.add(randomImgTwoResource);
        imageHolders.add(randomImgThreeResource);

        Log.i(LOG_TAG, imageHolders.get(0) + " " + imageHolders.get(1) + " " + imageHolders.get(2));

        Integer randomCarMakeInt = imageHolders.get(getRandomIndex(imageHolders));
        String randomCarMakeStr = context.getResources().getResourceEntryName(randomCarMakeInt);

        String[] words = randomCarMakeStr.split("[_]");
        String correctCarMake = words[1];
        correctCarMake = correctCarMake.substring(0, 1).toUpperCase() + correctCarMake.substring(1).toLowerCase();

        if (correctCarMake.equalsIgnoreCase("bmw")) {
            correctCarMake = correctCarMake.toUpperCase();
        }
        if (correctCarMake.equalsIgnoreCase("benz")) {
            correctCarMake = "Mercedes-Benz";
        }

        Log.i(LOG_TAG, imageHolders.get(0) + " " + imageHolders.get(1) + " " + imageHolders.get(2));

        randomCarMake.setText(correctCarMake);
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
                break;
            case R.id.car_img2:
                randomImageTwo.setBackgroundColor(Color.parseColor("#289995"));
                break;
            case R.id.car_img3:
                randomImageThree.setBackgroundColor(Color.parseColor("#289995"));
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
                break;
            case R.id.car_img2:
                randomImageTwo.setBackgroundColor(Color.parseColor("#ff9995"));
                break;
            case R.id.car_img3:
                randomImageThree.setBackgroundColor(Color.parseColor("#ff9995"));
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

    public int getRandomIndex(List<Integer> imageArr) {
        Random random = new Random();
        if (imageArr.size() > 0) {
            if (context.getClass().getSimpleName().equals("CarMakeActivity")) {
                Log.d(LOG_TAG, "! shuffling ! ");
                Collections.shuffle(imageArr);
            }

            return random.nextInt(imageArr.size());
        }
        return -1;
    }

    public List<Integer> getImageHolders() {
        return imageHolders;
    }

    public TextView getRandomCarMake() {
        return randomCarMake;
    }
}
