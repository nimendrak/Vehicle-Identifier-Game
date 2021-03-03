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
    private List<Integer> imageHolders;
    private List<Integer> logoArr;
    private Styles styles;

    private String correctCarMake;

    public PopulateData(Context context, ImageLoader imageLoader, Styles styles) {
        if (context.getClass().getSimpleName().equals("CarMakeActivity")) {
            this.context = context;
            this.imageLoader = imageLoader;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();
            this.styles = styles;

            setImagesTaskOne();
        }
        if (context.getClass().getSimpleName().equals("CarImageActivity")) {
            this.context = context;
            this.imageLoader = imageLoader;
            this.imgArr = imageLoader.getCarImagesArray();
            this.logoArr = imageLoader.getCarLogosArray();
            this.styles = styles;

            setImagesTaskThree();
        }
        styles.resetAnswer();
    }

    @SuppressLint("DefaultLocale")
    public void setImagesTaskOne() {
        // car
        Integer currentImageResource = imgArr.get(getRandomIndex(imgArr));
        String currentImgResourceName = context.getResources().getResourceEntryName(currentImageResource);

        styles.getCarImage().setImageResource(currentImageResource);
        styles.getCarImage().setTag(currentImgResourceName);

        // car logo
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

        styles.getRandomImageOne().setImageResource(randomImgOneResource);
        styles.getRandomImageOne().setTag(randomImgOneResourceName);

        styles.getRandomImageTwo().setImageResource(randomImgTwoResource);
        styles.getRandomImageTwo().setTag(randomImgTwoResourceName);

        styles.getRandomImageThree().setImageResource(randomImgThreeResource);
        styles.getRandomImageThree().setTag(randomImgThreeResourceName);

        imageHolders = new ArrayList<>();
        imageHolders.add(randomImgOneResource);
        imageHolders.add(randomImgTwoResource);
        imageHolders.add(randomImgThreeResource);

        Log.i(LOG_TAG, imageHolders.get(0) + " " + imageHolders.get(1) + " " + imageHolders.get(2));

        Integer randomCarMakeInt = imageHolders.get(getRandomIndex(imageHolders));
        String randomCarMakeStr = context.getResources().getResourceEntryName(randomCarMakeInt);

        String[] words = randomCarMakeStr.split("[_]");
        correctCarMake = words[1];
        correctCarMake = correctCarMake.substring(0, 1).toUpperCase() + correctCarMake.substring(1).toLowerCase();

        if (correctCarMake.equalsIgnoreCase("bmw")) {
            correctCarMake = correctCarMake.toUpperCase();
        }
        if (correctCarMake.equalsIgnoreCase("benz")) {
            correctCarMake = "Mercedes-Benz";
        }

        Log.i(LOG_TAG, imageHolders.get(0) + " " + imageHolders.get(1) + " " + imageHolders.get(2));

        styles.getRandomCarMake().setText(correctCarMake);
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

    public String getCorrectCarMake() {
        return correctCarMake;
    }
}
