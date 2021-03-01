package com.example.nimendra.utils;

import android.content.Context;

public class ValidateImages {
    private String selectedCar;
    private ImageLoader imageLoader;
    private Context context;

    public ValidateImages(Context current, String selectedCar, ImageLoader imageLoader) {
        this.context = current;
        this.selectedCar = selectedCar;
        this.imageLoader = imageLoader;
    }

    public boolean validation() {
        for (int i = 0; i < 5; i++) {
            if (context.getResources().getResourceEntryName(imageLoader.getAudiImagesArray().get(i)).contains(selectedCar.toLowerCase())) {
                imageLoader.getAudiImagesArray().remove(i);
                System.out.println(imageLoader.getAudiImagesArray().size());
                return true;
            }
        }


        return false;
    }
}
