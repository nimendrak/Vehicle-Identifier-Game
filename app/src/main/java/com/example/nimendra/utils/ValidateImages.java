package com.example.nimendra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.View;
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
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imageLoader.getCarImagesArray().remove(correctCarMakeIndex);
        return false;
    }

    // CarMakeActivity get correct car make
    public String getCorrectCarMakeTaskTwo() {
        ImageView imageView = activity.findViewById(R.id.car_image);
        String imageTag = (String) imageView.getTag();

        String correctCarMake;
        String[] words = imageTag.split("[_]");
        correctCarMake = words[1];
        correctCarMake = correctCarMake.substring(0, 1).toUpperCase() + correctCarMake.substring(1).toLowerCase();

        if (correctCarMake.equalsIgnoreCase("bmw")) {
            correctCarMake = correctCarMake.toUpperCase();
        }

        return correctCarMake;
    }

    // CarImageActivity image validations
    @SuppressLint("NonConstantResourceId")
    public boolean validation(Integer clickedImageId, String randomCarMakeStr, PopulateData populateData) {
        // CarImageActivity holds a array of randomly selected 3 different car images
        List<Integer> randomImgArr = populateData.getRandomImgArr();

        // r1, r2, r3 respectively placed in the UI image placeholders
        String r1 = context.getResources().getResourceEntryName(randomImgArr.get(0));
        String r2 = context.getResources().getResourceEntryName(randomImgArr.get(1));
        String r3 = context.getResources().getResourceEntryName(randomImgArr.get(2));

        // Get the clicked image tag which returns the resource name
        ImageView clickedImage = activity.findViewById(clickedImageId);
        String imageTag = (String) clickedImage.getTag();

        switch (clickedImageId) {
            case R.id.car_img1:
                if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r1)) {
                    return true;
                }
                break;
            case R.id.car_img2:
                if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r2)) {
                    return true;
                }
                break;
            case R.id.car_img3:
                if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r3)) {
                    return true;
                }
                break;
        }

        return false;
    }

    // CarImageActivity get correct car make
    public Integer getCorrectCarMakeTaskTwo(PopulateData populateData) {
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
        // Declare a boolean flag to indicate whether input is correct or wrong
        boolean isCorrect = false;

        String[] wordFrmImg, lettersFrmImg, lettersFrmText;

        // Get current image tag and put it to an arr -> lettersFrmImg
        ImageView currentImg = activity.findViewById(R.id.car_image);
        String currentImgText = (String) currentImg.getTag();
        // Image tag is like -> car_audi_1
        // So, it need to split by _
        wordFrmImg = currentImgText.split("[_]");
        // Then, put only the car make letters into another arr -> lettersFrmImg
        // By splitting word by ""
        lettersFrmImg = wordFrmImg[1].split("");

        // Get current car make text and put it to an arr -> lettersFrmText
        // Basically this contains "-" only!
        TextView imgText = activity.findViewById(R.id.random_car_make);
        String imgTextStr = (String) imgText.getText();
        lettersFrmText = imgTextStr.split("");

        // Get user input -> single character
        String inputStr = inputChar.toString().toLowerCase();
        // Only the correct letter's index replaced with the specific letter
        // And, rest of the letters stay as "-"
        for (int i = 0; i < lettersFrmImg.length; i++) {
            if (inputStr.equals(lettersFrmImg[i])) {
                lettersFrmText[i] = inputStr;
                isCorrect = true;
            }
        }
        StringBuilder builder = new StringBuilder();
        // Build the current text of the car make with letters and "-"
        for (String string : lettersFrmText) {
            if (builder.length() > 0) {
                builder.append("");
            }
            builder.append(string);
        }
        String string = builder.toString().toUpperCase();
        // Set the current string
        imgText.setText(string);

        Log.d(LOG_TAG, "set text -> " + string);

        return isCorrect;
    }

    public String getCorrectCarMakeTaskThree() {
        TextView imgText = activity.findViewById(R.id.random_car_make);
        String imgTextStr = (String) imgText.getText();

        ImageView currentImg = activity.findViewById(R.id.car_image);
        String currentImgText = (String) currentImg.getTag();

        if (currentImgText.contains(imgTextStr.toLowerCase())) {
            imgTextStr = imgTextStr.substring(0, 1).toUpperCase() + imgTextStr.substring(1).toLowerCase();
            if (imgTextStr.equalsIgnoreCase("bmw")) {
                imgTextStr = imgTextStr.toUpperCase();
            }
            return imgTextStr;
        }
        return null;
    }

    public int validation(String answer, PopulateData populateData) {
        // CarImageActivity holds a array of randomly selected 3 different car images
        List<Integer> randomImgArr = populateData.getRandomImgArr();

        // r1, r2, r3 respectively placed in the UI image placeholders
        String r1 = context.getResources().getResourceEntryName(randomImgArr.get(0));
        String r2 = context.getResources().getResourceEntryName(randomImgArr.get(1));
        String r3 = context.getResources().getResourceEntryName(randomImgArr.get(2));

        String r1CarMake, r2CarMake, r3CarMake;
        String[] words;

        words = r1.split("[_]");
        r1CarMake = words[1];
        if (r1CarMake.equals(answer)) {
            Log.d(LOG_TAG, "0 -> " + answer);
            return 0;
        }

        words = r2.split("[_]");
        r2CarMake = words[1];
        if (r2CarMake.equals(answer)) {
            Log.d(LOG_TAG, "1 -> " + answer);
            return 1;
        }

        words = r3.split("[_]");
        r3CarMake = words[1];
        if (r3CarMake.equals(answer)) {
            Log.d(LOG_TAG, "2 -> " + answer);
            return 2;
        }
        return -1;
    }

    public String getCorrectCarMakeTaskFour(PopulateData populateData, int index) {
        // CarImageActivity holds a array of randomly selected 3 different car images
        List<Integer> randomImgArr = populateData.getRandomImgArr();

        // r1, r2, r3 respectively placed in the UI image placeholders
        String r1 = context.getResources().getResourceEntryName(randomImgArr.get(0));
        String r2 = context.getResources().getResourceEntryName(randomImgArr.get(1));
        String r3 = context.getResources().getResourceEntryName(randomImgArr.get(2));

        String [] words;
        String correctCarMake;

        switch (index) {
            case 0:
                words = r1.split("[_]");
                correctCarMake = words[1].substring(0, 1).toUpperCase() + words[1].substring(1).toLowerCase();
                return correctCarMake;
            case 1:
                words = r2.split("[_]");
                correctCarMake = words[1].substring(0, 1).toUpperCase() + words[1].substring(1).toLowerCase();
                return correctCarMake;
            case 2:
                words = r3.split("[_]");
                correctCarMake = words[1].substring(0, 1).toUpperCase() + words[1].substring(1).toLowerCase();
                return correctCarMake;
        }
        return null;
    }

}