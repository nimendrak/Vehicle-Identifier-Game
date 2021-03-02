package com.example.nimendra.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.nimendra.R;

import java.util.Random;

public class ValidateImages {

    // Class name for Log tag
    private static final String LOG_TAG = ValidateImages.class.getSimpleName();

    private Activity activity;
    private Context context;
    private ImageLoader imageLoader;

    public ValidateImages(Activity activity, Context context, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        this.context = context;
        this.activity = activity;
    }

    public boolean validation() {
        ImageView currentImg = (ImageView) activity.findViewById(R.id.car_image);
        String currentCar = (String) currentImg.getTag();

        if (imageLoader.getCarImagesArray().size() > 0) {
            try {
                for (int i = 0; i < imageLoader.getCarImagesArray().size(); i++) {
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
        String correctCarMake;
        ImageView imageView = activity.findViewById(R.id.car_image);
        String imageTag = (String) imageView.getTag();
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
}