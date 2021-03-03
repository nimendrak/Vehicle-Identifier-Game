package com.example.nimendra.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.nimendra.R;

public class ValidateImages {

    // Class name for Log tag
    private static final String LOG_TAG = ValidateImages.class.getSimpleName();

    private Activity activity;
    private Context context;
    private String selectedCar;
    private ImageLoader imageLoader;

    public ValidateImages(Activity activity, Context context, ImageLoader imageLoader, String selectedCar) {
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
        ImageView currentImg = (ImageView) activity.findViewById(R.id.car_image);
        String currentImgResourceName = (String) currentImg.getTag();

        int correctCarMakeIndex = 0;
        if (imageLoader.getCarImagesArray().size() > 0) {
            try {
                for (int i = 0; i < imageLoader.getCarImagesArray().size(); i++) {
                    if (currentImgResourceName.contains(selectedCar.toLowerCase())) {
                        correctCarMakeIndex = i;
                        imageLoader.getCarImagesArray().remove(correctCarMakeIndex);
                        Log.d(LOG_TAG, "Images Array size - " + imageLoader.getCarImagesArray().size());
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imageLoader.getCarImagesArray().remove(correctCarMakeIndex);
        Log.d(LOG_TAG, "Images Array size - " + imageLoader.getCarImagesArray().size());
        return false;
    }

    public boolean validation(Integer clickedImageId, String randomCarMakeStr, PopulateData populateData) {

        String r1 = context.getResources().getResourceEntryName(populateData.getImageHolders().get(0));
        String r2 = context.getResources().getResourceEntryName(populateData.getImageHolders().get(1));
        String r3 = context.getResources().getResourceEntryName(populateData.getImageHolders().get(2));

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
                if (imageTag.contains(randomCarMakeStr.toLowerCase())  & imageTag.contains(r2)) {
                    return true;
                }
                break;
            case R.id.car_img3:
                Log.d(LOG_TAG, "tag - " + imageTag);
                Log.d(LOG_TAG, "random car make - " + randomCarMakeStr);
                Log.d(LOG_TAG, "r3 - " + r3);
                if (imageTag.contains(randomCarMakeStr.toLowerCase())  & imageTag.contains(r3)) {
                    return true;
                }
                break;
        }

        return false;
    }

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
}