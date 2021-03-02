package com.example.nimendra.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.nimendra.R;

import java.lang.reflect.Field;
import java.util.Random;

public class ValidateImages {

    // Class name for Log tag
    private static final String LOG_TAG = ValidateImages.class.getSimpleName();

    private static Field[] carImagesArray = R.drawable.class.getDeclaredFields();

    private Activity activity;
    private Context context;
    private String selectedCar;
    private ImageLoader imageLoader;

    public ValidateImages(Activity activity, Context context, String selectedCar, ImageLoader imageLoader) {
        this.selectedCar = selectedCar;
        this.imageLoader = imageLoader;
        this.context = context;
        this.activity = activity;
    }

    public ValidateImages(Activity activity, Context context, ImageLoader imageLoader) {
        this.activity = activity;
        this.context = context;
        this.imageLoader = imageLoader;
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

    public String getCorrectCarMake() {

        ImageView imageView = activity.findViewById(R.id.car_image);
        int text = (int) imageView.getTag();
        Log.d(LOG_TAG, "imageView.getTag - " + text);


        String correctCarMake = null;
        for (Field f : carImagesArray) {
            try {
                if (text == f.getInt(null)) {
                    Log.d(LOG_TAG, "f.getInt - " + f.getInt(null));
                    Log.d(LOG_TAG, "f.getName - " + f.getName());
                    String[] words = f.getName().split("[_]");
                    correctCarMake = words[1];
                    correctCarMake = correctCarMake.substring(0, 1).toUpperCase() + correctCarMake.substring(1).toLowerCase();
                }
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (correctCarMake != null) {
            if (correctCarMake.equalsIgnoreCase("bmw")) {
                correctCarMake = correctCarMake.toUpperCase();
            }
            if (correctCarMake.equalsIgnoreCase("benz")) {
                correctCarMake = "Mercedes-Benz";
            }
        }

        Log.d(LOG_TAG, "correct car - " + correctCarMake);

        return correctCarMake;
    }
}