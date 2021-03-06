package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchCarMakeActivity(View view) {
        Intent intent = new Intent(this, CarMakeActivity.class);
        startActivity(intent);
    }

    public void launchCarImageActivity(View view) {
        Intent intent = new Intent(this, CarImageActivity.class);
        startActivity(intent);
    }

    public void launchHintsActivity(View view) {
        Intent intent = new Intent(this, CarHintActivity.class);
        startActivity(intent);
    }

    public void launchAdvancedLevelActivity(View view) {
        Intent intent = new Intent(this, AdvancedActivity.class);
        startActivity(intent);
    }
}