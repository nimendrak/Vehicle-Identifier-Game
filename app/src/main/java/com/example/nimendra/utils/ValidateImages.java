package com.example.nimendra.utils;

import android.content.Context;
import android.util.Log;

import com.example.nimendra.CarMakeActivity;

import java.util.Random;

public class ValidateImages {

    // Class name for Log tag
    private static final String LOG_TAG = ValidateImages.class.getSimpleName();

    private Context context;
    private String selectedCar;
    private ImageLoader imageLoader;

    private String getSelectedCar;

    public ValidateImages(Context context, String selectedCar, ImageLoader imageLoader) {
        this.selectedCar = selectedCar;
        this.imageLoader = imageLoader;
        this.context = context;
    }

    public boolean validation() {
        if (imageLoader.getCarImagesArray().size() > 0) {
            try {
                for (int i = 0; i < imageLoader.getCarImagesArray().size(); i++) {

                    String currentCar = "car_" + selectedCar.toLowerCase() + "_" + (i + 1);
                    String carFromArr = context.getResources().getResourceEntryName(imageLoader.getCarImagesArray().get(i));

                    if (currentCar.equalsIgnoreCase(carFromArr)) {
                        Log.d(LOG_TAG, "current car - " + currentCar);
                        Log.d(LOG_TAG, "from arr - " + carFromArr);

                        imageLoader.getCarImagesArray().remove(i);
                        Log.d(LOG_TAG, "Images Array size - " + imageLoader.getCarImagesArray().size());
                        return true;
                    } else {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(LOG_TAG, "Images Array size - " + imageLoader.getCarImagesArray().size());
        return false;
    }

    public int getRandom() {
        if (imageLoader.getCarImagesArray().size() > 0) {
            Random random = new Random();
            return random.nextInt(imageLoader.getCarImagesArray().size());
        }
        return -1;
    }

}