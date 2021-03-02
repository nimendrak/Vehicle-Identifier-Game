package com.example.nimendra.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private List<Integer> carImagesArray = new ArrayList<>();
    private List<Integer> carLogosArray = new ArrayList<>();

    private Context context;

    public ImageLoader(Context current) {
        this.context = current;
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
//                int teslaImages = context.getResources().getIdentifier(("car_tesla_" + i), "drawable", context.getPackageName());
//                carImagesArray.add(teslaImages);
            }
            int audiLogo = context.getResources().getIdentifier("car_audi", "drawable", context.getPackageName());
            carLogosArray.add(audiLogo);

            int bmwLogo = context.getResources().getIdentifier("car_bmw", "drawable", context.getPackageName());
            carLogosArray.add(bmwLogo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("images arr - " + carImagesArray);
        System.out.println("logos arr size  - " + carImagesArray.size());
        System.out.println("logos arr - " + carLogosArray);
        System.out.println("images arr size - " + carLogosArray.size());
    }

    public List<Integer> getCarImagesArray() {
        return carImagesArray;
    }

    public List<Integer> getCarLogosArray() {
        return carLogosArray;
    }
}
