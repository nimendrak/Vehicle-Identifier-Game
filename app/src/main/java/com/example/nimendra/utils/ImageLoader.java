package com.example.nimendra.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private List<Integer> audiImagesArray = new ArrayList<>();
    private List<Integer> bmwImagesArray = new ArrayList<>();
    private List<Integer> bugattiImagesArray = new ArrayList<>();
    private List<Integer> ferrariImagesArray = new ArrayList<>();
    private List<Integer> koenigseggImagesArray = new ArrayList<>();
    private List<Integer> mercImagesArray = new ArrayList<>();
    private List<Integer> porscheImagesArray = new ArrayList<>();
    private List<Integer> teslaImagesArray = new ArrayList<>();

    private Context context;

    public ImageLoader(Context current){
        this.context = current;
        loadData();
    }

    public void loadData() {
        try {
            for (int i = 1; i < 6; i++) {
                int audiImages = context.getResources().getIdentifier(("car_audi_" + i), "drawable", context.getPackageName());
                audiImagesArray.add(audiImages);

                int bugattiImages = context.getResources().getIdentifier(("car_bugatti_" + i), "drawable", context.getPackageName());
                bugattiImagesArray.add(bugattiImages);

                int bmwImages = context.getResources().getIdentifier(("car_bmw_" + i), "drawable", context.getPackageName());
                bmwImagesArray.add(bmwImages);

                int ferrariImages = context.getResources().getIdentifier(("car_ferrari_" + i), "drawable", context.getPackageName());
                ferrariImagesArray.add(ferrariImages);

                int koenigseggImages = context.getResources().getIdentifier(("car_koenigsegg_" + i), "drawable", context.getPackageName());
                koenigseggImagesArray.add(koenigseggImages);

                int porscheImages = context.getResources().getIdentifier(("car_porsche_" + i), "drawable", context.getPackageName());
                porscheImagesArray.add(porscheImages);

                int teslaImages = context.getResources().getIdentifier(("car_tesla_" + i), "drawable", context.getPackageName());
                teslaImagesArray.add(teslaImages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(audiImagesArray);
    }

    public List<Integer> getAudiImagesArray() {
        return audiImagesArray;
    }

    public List<Integer> getBugattiImagesArray() {
        return bugattiImagesArray;
    }

    public List<Integer> getBmwImagesArray() {
        return bmwImagesArray;
    }

    public List<Integer> getFerrariImagesArray() {
        return ferrariImagesArray;
    }

    public List<Integer> getKoenigseggImagesArray() {
        return koenigseggImagesArray;
    }

    public List<Integer> getMercImagesArray() {
        return mercImagesArray;
    }

    public List<Integer> getPorscheImagesArray() {
        return porscheImagesArray;
    }

    public List<Integer> getTeslaImagesArray() {
        return teslaImagesArray;
    }
}
