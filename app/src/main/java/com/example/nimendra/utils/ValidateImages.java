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
        String currentImgTag = (String) currentImg.getTag();

        try {
            if (selectedCar != null) {
                if (currentImgTag.contains(selectedCar.toLowerCase())) {
                    removeImgArr(currentImgTag);
                    return true;
                } else {
                    removeImgArr(currentImgTag);
                    return false;
                }
            } else {
                removeImgArr(currentImgTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // boolean flag
        boolean isCorrect = false;

        // randomImgArr holds a randomly selected 3 images from the loaded car images arr
        List<Integer> randomImgArr = populateData.getRandomImgArr();

        // r1, r2, r3 respectively placed in the UI image placeholders
        String r1 = context.getResources().getResourceEntryName(randomImgArr.get(0));
        String r2 = context.getResources().getResourceEntryName(randomImgArr.get(1));
        String r3 = context.getResources().getResourceEntryName(randomImgArr.get(2));

        try {
            // Get the clicked image tag which returns the resource name
            ImageView clickedImage = activity.findViewById(clickedImageId);
            String imageTag = (String) clickedImage.getTag();

            switch (clickedImageId) {
                case R.id.car_img1:
                    if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r1)) {
                        // Either answer is correct or wrong
                        // Remove current 3 images from the loaded arr
                        removeImgArr(r1);
                        removeImgArr(r2);
                        removeImgArr(r3);
                        isCorrect = true;
                    }
                    break;
                case R.id.car_img2:
                    if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r2)) {
                        removeImgArr(r1);
                        removeImgArr(r2);
                        removeImgArr(r3);
                        isCorrect = true;
                    }
                    break;
                case R.id.car_img3:
                    if (imageTag.contains(randomCarMakeStr.toLowerCase()) & imageTag.contains(r3)) {
                        removeImgArr(r1);
                        removeImgArr(r2);
                        removeImgArr(r3);
                        isCorrect = true;
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isCorrect;
    }

    // CarImageActivity get correct car make
    public Integer getCorrectCarMakeTaskTwo(PopulateData populateData) {
        // randomImgArr holds a randomly selected 3 images from the loaded car images arr
        List<Integer> randomImgArr = populateData.getRandomImgArr();
        // populatedImgArr holds the current image holders IDs
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

        return isCorrect;
    }

    // CarHintActivity get correct car make
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

    // AdvancedActivity image validations
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
        if (r1CarMake.equals(answer.toLowerCase())) {
            return 0;
        }

        words = r2.split("[_]");
        r2CarMake = words[1];
        if (r2CarMake.equals(answer.toLowerCase())) {
            return 1;
        }

        words = r3.split("[_]");
        r3CarMake = words[1];
        if (r3CarMake.equals(answer.toLowerCase())) {
            return 2;
        }
        return -1;
    }

    // AdvancedActivity get correct car make
    public String getCorrectCarMakeTaskFour(PopulateData populateData, int index) {
       // CarImageActivity holds a array of randomly selected 3 different car images
        List<Integer> randomImgArr = populateData.getRandomImgArr();

        // r1, r2, r3 respectively placed in the UI image placeholders
        String r1 = context.getResources().getResourceEntryName(randomImgArr.get(0));
        String r2 = context.getResources().getResourceEntryName(randomImgArr.get(1));
        String r3 = context.getResources().getResourceEntryName(randomImgArr.get(2));

        String[] words;
        String correctCarMake = null;

        switch (index) {
            case 0:
                words = r1.split("[_]");
                correctCarMake = words[1].substring(0, 1).toUpperCase() + words[1].substring(1).toLowerCase();
                break;
            case 1:
                words = r2.split("[_]");
                correctCarMake = words[1].substring(0, 1).toUpperCase() + words[1].substring(1).toLowerCase();
                break;
            case 2:
                words = r3.split("[_]");
                correctCarMake = words[1].substring(0, 1).toUpperCase() + words[1].substring(1).toLowerCase();
                break;
        }

        if (correctCarMake.equalsIgnoreCase("bmw")) {
            correctCarMake = correctCarMake.toUpperCase();
        }

        return correctCarMake;
    }

    // Remove images from the loaded car images arr
    public void removeImgArr(String currentImgText) {
        for (int i = 0; i < imageLoader.getCarImagesArray().size(); i++) {
            String nameFromArr = context.getResources().getResourceEntryName(imageLoader.getCarImagesArray().get(i));
            // If selected car is wrong or null, remove it from the image arr
            if (currentImgText.toLowerCase().equals(nameFromArr)) {
                imageLoader.getCarImagesArray().remove(i);
                break;
            }
        }
    }

    // Overloaded method for remove images
    // Used in Task -> Advanced Activity
    public void removeImgArr() {
        ImageView currentImgOne = activity.findViewById(R.id.car_img1);
        String currentImgTextOne = (String) currentImgOne.getTag();
        removeImgArr(currentImgTextOne);

        ImageView currentImgTwo = activity.findViewById(R.id.car_img2);
        String currentImgTextTwo = (String) currentImgTwo.getTag();
        removeImgArr(currentImgTextTwo);

        ImageView currentImgThree = activity.findViewById(R.id.car_img3);
        String currentImgTextThree = (String) currentImgThree.getTag();
        removeImgArr(currentImgTextThree);

    }

}