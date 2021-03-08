package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.nimendra.utils.ImageLoader;
import com.example.nimendra.utils.PopulateData;
import com.example.nimendra.utils.Styles;
import com.example.nimendra.utils.ValidateImages;

public class AdvancedActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = AdvancedActivity.class.getSimpleName();

    private ValidateImages validateImages;
    private PopulateData populateData;
    private ImageLoader imageLoader;
    private Styles styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        imageLoader = new ImageLoader(this);
        validateImages = new ValidateImages(AdvancedActivity.this, this, imageLoader);
        styles = new Styles(AdvancedActivity.this, this);
        populateData = new PopulateData( this, imageLoader, styles);
    }

    public void validateAnswer(View view) {
    }
}