package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // Unique tag required for the intent extra
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchCarMake(View view) {
        Intent intent = new Intent(this, CarMakeActivity.class);
        startActivity(intent);
    }

    public void launchCarImage(View view) {
        Intent intent = new Intent(this, CarImageActivity.class);
        startActivity(intent);
    }
}