package com.example.nimendra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nimendra.R;

import java.util.Arrays;
import java.util.List;

public class ValidateImages {

    // Class name for Log tag
    private static final String LOG_TAG = ValidateImages.class.getSimpleName();

    private final Activity activity;
    private final Context context;
    private final ImageLoader imageLoader;

    public ValidateImages(Activity activity, Context context, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        this.context = context;
        this.activity = activity;
    }

    // CarMakeActivity image validations
    public boolean validation(String selectedCar) {
        ImageView currentImg = activity.findViewById(R.id.car_image);
        String currentImgResourceName = (String) currentImg.getTag();

        int correctCarMakeIndex = 0;
        if (imageLoader.getCarImagesArray().size() > 0) {
            try {
                for (int i = 0; i < imageLoader.getCarImagesArray().size(); i++) {
                    if (currentImgResourceName.contains(selectedCar.toLowerCase())) {
                        correctCarMakeIndex = i;
                        imageLoader.getCarImagesArray().remove(correctCarMakeIndex);
                        Log.d(LOG_TAG, "Task 01 img arr size - " + imageLoader.getCarImagesArray().size());
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imageLoader.getCarImagesArray().remove(correctCarMakeIndex);
        Log.d(LOG_TAG, "Task 01 img arr size - " + imageLoader.getCarImagesArray().size());
        return false;
    }

    // CarMakeActivity get correct car make
    public String getCorrectCarMake() {
        ImageView imageView = activity.findViewById(R.id.car_image);
        String imageTag = (String) imageView.getTag();

        String correctCarMake;
        String[] words = imageTag.split("[_]");
        correctCarMake = words[1];
        correctCarMake = correctCarMake.substring(0, 1).toUpperCase() + correctCarMake.substring(1).toLowerCase();

        if (correctCarMake.equalsIgnoreCase("bmw")) {
            correctCarMake = correctCarMake.toUpperCase();
        }
        if (correctCarMake.equalsIgnoreCase("benz")) {
            correctCarMake = "Mercedes-Benz";
        }

        Log.d(LOG_TAG, "correct car - " + correctCarMake);

        return correctCarMake;
    }

    // CarImageActivity image validations
    @SuppressLint("NonConstantResourceId")
    public boolean validation(Integer clickedImageId, String randomCarMakeStr, PopulateData populateData) {

        List<Integer> randomImgArr = populateData.getRandomImgArr();

        String r1 = context.getResources().getResourceEntryName(randomImgArr.get(0));
        String r2 = context.getResources().getResourceEntryName(randomImgArr.get(1));
        String r3 = context.getResources().getResourceEntryName(randomImgArr.get(2));

        Log.d(LOG_TAG, "arr - " + r1 + ", " + r2 + " ," + r3);

        String imageTag;
        ImageView clickedImage = activity.findViewById(clickedImageId);
        imageTag = (String) clickedImage.getTag();

        switch (clickedImageId) {
            case R.id.car_img1:
                Log.d(LOG_TAG, "tag - " + imageTag);
                Log.d(LOG_TAG, "random car make - " + randomCarMakeStr);
                Log.d(LOG_TAG, "r1 - " + r1);
                if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r1)) {
                    return true;
                }
                break;
            case R.id.car_img2:
                Log.d(LOG_TAG, "tag - " + imageTag);
                Log.d(LOG_TAG, "random car make - " + randomCarMakeStr);
                Log.d(LOG_TAG, "r2 - " + r2);
                if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r2)) {
                    return true;
                }
                break;
            case R.id.car_img3:
                Log.d(LOG_TAG, "tag - " + imageTag);
                Log.d(LOG_TAG, "random car make - " + randomCarMakeStr);
                Log.d(LOG_TAG, "r3 - " + r3);
                if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r3)) {
                    return true;
                }
                break;
        }

        return false;
    }

    // CarImageActivity get correct car make
    public Integer getCorrectCarMake(PopulateData populateData) {
        List<Integer> randomImgArr = populateData.getRandomImgArr();
        List<Integer> populatedImgArr = Arrays.asList(R.id.car_img1, R.id.car_img2, R.id.car_img3);

        TextView currentTextView = activity.findViewById(R.id.random_car_make);
        String clickedImgStr = (String) currentTextView.getText();

        Integer correctImgId = null;
        for (int i = 0; i < populateData.getRandomImgArr().size(); i++) {
            String randomImgStr = context.getResources().getResourceEntryName(randomImgArr.get(i));
            if (randomImgStr.contains(clickedImgStr.toLowerCase())) {
                correctImgId = populatedImgArr.get(i);
                break;
            }
        }
        return correctImgId;
    }

    // CarHintActivity input validations
    public boolean validation(Editable inputChar) {
        boolean isCorrect = false;

        String[] wordFrmImg;
        String[] lettersFrmImg, lettersFrmText;

        // get current image tag and put it to an arr -> lettersFrmImg
        ImageView currentImg = activity.findViewById(R.id.car_image);
        String currentImgText = (String) currentImg.getTag();
        wordFrmImg = currentImgText.split("[_]");
        lettersFrmImg = wordFrmImg[1].split("");

        // get current car make text and put it to an arr -> lettersFrmText
        // basically this contains "-" only!
        TextView imgText = activity.findViewById(R.id.random_car_make);
        String imgTextStr = (String) imgText.getText();
        lettersFrmText = imgTextStr.split("");

        // get user input -> single character
        String inputStr = inputChar.toString().toLowerCase();

        for (int i = 0; i < lettersFrmImg.length; i++) {
            if (inputStr.equals(lettersFrmImg[i])) {
                lettersFrmText[i] = inputStr;
                isCorrect = true;
            }
        }
        StringBuilder builder = new StringBuilder();

        for (String string : lettersFrmText) {
            if (builder.length() > 0) {
                builder.append("");
            }
            builder.append(string);
        }

        String string = builder.toString().toUpperCase();
        Log.d(LOG_TAG, "set text -> " + string);

        imgText.setText(string);

        return isCorrect;
    }

    public String getCorrectCarMakeTaskThree() {
        TextView imgText = activity.findViewById(R.id.random_car_make);
        String imgTextStr = (String) imgText.getText();

        ImageView currentImg = activity.findViewById(R.id.car_image);
        String currentImgText = (String) currentImg.getTag();

        Log.d(LOG_TAG, "return value -> " + currentImgText + ", " + imgTextStr);

        if (currentImgText.contains(imgTextStr.toLowerCase())) {
            imgTextStr = imgTextStr.substring(0, 1).toUpperCase() + imgTextStr.substring(1).toLowerCase();
            if (imgTextStr.equalsIgnoreCase("bmw")) {
                imgTextStr = imgTextStr.toUpperCase();
            }
            if (imgTextStr.equalsIgnoreCase("benz")) {
                imgTextStr = "Mercedes";
            }
            return imgTextStr;
        }
        return null;
    }
}