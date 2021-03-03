package com.example.nimendra.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ImageLoader {

    // Class name for Log tag
    private static final String LOG_TAG = ImageLoader.class.getSimpleName();

    private List<Integer> carImagesArray = new ArrayList<>();
    private List<Integer> carLogosArray = new ArrayList<>();

    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
        loadData();
    }

    public void loadData() {
        try {
            for (int i = 0; i < 5; i++) {
                int audiImages = context.getResources().getIdentifier(("car_audi_" + (i + 1)), "drawable", context.getPackageName());
                carImagesArray.add(audiImages);

                int bmwImages = context.getResources().getIdentifier(("car_bmw_" + (i + 1)), "drawable", context.getPackageName());
                carImagesArray.add(bmwImages);

//                int bugattiImages = context.getResources().getIdentifier(("car_bugatti_" + i), "drawable", context.getPackageName());
//                carImagesArray.add(bugattiImages);

//                int ferrariImages = context.getResources().getIdentifier(("car_ferrari_" + i), "drawable", context.getPackageName());
//                carImagesArray.add(ferrariImages);

//                int koenigseggImages = context.getResources().getIdentifier(("car_koenigsegg_" + i), "drawable", context.getPackageName());
//                carImagesArray.add(koenigseggImages);
//
//                int porscheImages = context.getResources().getIdentifier(("car_porsche_" + i), "drawable", context.getPackageName());
//                carImagesArray.add(porscheImages);
//
                int teslaImages = context.getResources().getIdentifier(("car_tesla_" + (i + 1)), "drawable", context.getPackageName());
                carImagesArray.add(teslaImages);
            }
            int audiLogo = context.getResources().getIdentifier("car_audi", "drawable", context.getPackageName());
            carLogosArray.add(audiLogo);

            int bmwLogo = context.getResources().getIdentifier("car_bmw", "drawable", context.getPackageName());
            carLogosArray.add(bmwLogo);

            int teslaLogo = context.getResources().getIdentifier("car_tesla", "drawable", context.getPackageName());
            carLogosArray.add(teslaLogo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Logos arr size  - " + carImagesArray.size());
        Log.d(LOG_TAG, "Images arr size - " + carLogosArray.size());
    }

    public List<Integer> getCarImagesArray() {
        return carImagesArray;
    }

    public List<Integer> getCarLogosArray() {
        return carLogosArray;
    }
}
