package com.example.nimendra.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PopulateData {

    private static final String LOG_TAG = PopulateData.class.getSimpleName();

    private ImageLoader imageLoader;
    private Context context;

    private List<Integer> imgArr;
    private List<Integer> logoArr;
    private List<Integer> randomImgArr;

    private Styles styles;

    private final String activityName;

    public PopulateData(Context context, ImageLoader imageLoader, Styles styles) {
        activityName = context.getClass().getSimpleName();

        if (activityName.equals("CarMakeActivity")) {
            this.context = context;
            this.imageLoader = imageLoader;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();
            this.styles = styles;

            setImagesTaskOne();
        }
        if (activityName.equals("CarHintActivity")) {
            this.context = context;
            this.imageLoader = imageLoader;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();
            this.styles = styles;

            setImagesTaskTwo();
        }
        if (activityName.equals("CarImageActivity")) {
            this.context = context;
            this.imageLoader = imageLoader;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();
            this.styles = styles;

            setImagesTaskThree();
        }
        if (activityName.equals("AdvancedActivity")) {
            this.context = context;
            this.imageLoader = imageLoader;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();
            this.styles = styles;

            setImagesTaskFour();
        }
        styles.resetAnswer();
    }

    @SuppressLint("DefaultLocale")
    public void setImagesTaskOne() {
        // Pick a random image from the current loaded img arr
        Integer currentImageResource = imgArr.get(getRandomIndex(imgArr));
        // And get it source name
        String currentImgResourceName = context.getResources().getResourceEntryName(currentImageResource);

        // Set resource of the ImageView as the picked random img
        styles.getCarImage().setImageResource(currentImageResource);
        // Set its name as the tag
        styles.getCarImage().setTag(currentImgResourceName);

        // Loop will picked the car makes logo and set it with car img
        // Also, it sets the current ID of the img (ex: 01/45)
        try {
            for (int i = 0; i < logoArr.size(); i++) {
                Integer currentLogoResource = logoArr.get(i);
                String currentLogoResourceName = context.getResources().getResourceEntryName(currentLogoResource);

                if (currentImgResourceName.contains(currentLogoResourceName)) {
                    styles.getCarMakeLogo().setImageResource(currentLogoResource);
                    styles.getCarMakeLogo().setTag(currentLogoResourceName);

                    int id = imageLoader.getCarImagesArray().size();
                    styles.getCarId().setText(String.format("%02d", id));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("DefaultLocale")
    public void setImagesTaskTwo() {
        // Same as task one but needs one more attribute
        // Which is the TextView of the current car
        // All the characters set as dashes
        setImagesTaskOne();

        styles.getCarId().setText(String.format("%02d", 3));
        String correctCarMake;
        if (activityName.equals("CarHintActivity")) {
            String carMakeStr = (String) styles.getCarImage().getTag();
            String[] words = carMakeStr.split("[_]");
            correctCarMake = words[1].toUpperCase();

            String dashStr = correctCarMake.replaceAll("[a-zA-Z]", "_");
            styles.getRandomCarMake().setText(dashStr);
        }
    }

    public void setImagesTaskThree() {
        String correctCarMake;

        // Get the Randomly picked 3 different car images from the TaskFour
        // And holds it in a new arr randomImgArr
        randomImgArr = setImagesTaskFour();

        Integer randomCarMakeInt = randomImgArr.get(getRandomIndex(randomImgArr));
        String randomCarMakeStr = context.getResources().getResourceEntryName(randomCarMakeInt);

        String[] words = randomCarMakeStr.split("[_]");
        correctCarMake = words[1];
        correctCarMake = correctCarMake.substring(0, 1).toUpperCase() + correctCarMake.substring(1).toLowerCase();

        if (correctCarMake.equalsIgnoreCase("bmw")) {
            correctCarMake = correctCarMake.toUpperCase();
        }

        styles.getRandomCarMake().setText(correctCarMake);
    }

    public List<Integer> setImagesTaskFour() {
        randomImgArr = new ArrayList<>();

        Integer randomImgOneResource, randomImgTwoResource, randomImgThreeResource;
        String randomImgOneResourceName, randomImgTwoResourceName, randomImgThreeResourceName;
        String[] carOne, carTwo, carThree;

        boolean isCorrect = true;

        // Loop until it get 3 different car images
        // And put them into a Integer arr and returns it
        // Also, it randomized
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
                    randomImgArr.add(randomImgOneResource);
                    randomImgArr.add(randomImgTwoResource);
                    randomImgArr.add(randomImgThreeResource);
                    isCorrect = false;
                }
            }
        } while (isCorrect);

        styles.getRandomImageOne().setImageResource(randomImgOneResource);
        styles.getRandomImageOne().setTag(randomImgOneResourceName);

        styles.getRandomImageTwo().setImageResource(randomImgTwoResource);
        styles.getRandomImageTwo().setTag(randomImgTwoResourceName);

        styles.getRandomImageThree().setImageResource(randomImgThreeResource);
        styles.getRandomImageThree().setTag(randomImgThreeResourceName);

        return randomImgArr;
    }

    public int getRandomIndex(List<Integer> imageArr) {
        Random random = new Random();
        if (imageArr.size() > 0) {
            if (!context.getClass().getSimpleName().equals("CarImageActivity")) {
                // Shuffle the arr before picking the random index
                Collections.shuffle(imageArr);
            }
            return random.nextInt(imageArr.size());
        }
        return -1;
    }

    public List<Integer> getRandomImgArr() {
        return randomImgArr;
    }
}
