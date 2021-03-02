package com.example.nimendra.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private List<Integer> carImagesArray = new ArrayList<>();
    private Context context;

    public ImageLoader(Context current){
        this.context = current;
        loadData();
    }

    public void loadData() {
        try {
            for (int i = 0; i < 5; i++) {
                int audiImages = context.getResources().getIdentifier(("car_audi_" + (i+1)), "drawable", context.getPackageName());
                carImagesArray.add(audiImages);

                int bmwImages = context.getResources().getIdentifier(("car_bmw_" + (i+1)), "drawable", context.getPackageName());
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
//                int teslaImages = context.getResources().getIdentifier(("car_tesla_" + i), "drawable", context.getPackageName());
//                carImagesArray.add(teslaImages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loaded Image Array Size - " + carImagesArray.size());
    }

    public List<Integer> getCarImagesArray() {
        return carImagesArray;
    }
}
