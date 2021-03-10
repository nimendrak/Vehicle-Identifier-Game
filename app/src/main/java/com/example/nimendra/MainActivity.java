package com.example.nimendra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    // Class name for Log tag
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // Hold the value of Switch Stats
    boolean switchOn;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchMaterial switch1 = (SwitchMaterial) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Get boolean value from parameter
                if (isChecked) {
                    switchOn = true;
                    Log.d(LOG_TAG, "switch checked");
                } else {
                    switchOn = false;
                    Log.d(LOG_TAG, "switch not checked");
                }
            }
        });
    }

    public void launchCarMakeActivity(View view) {
        Intent intent = new Intent(this, CarMakeActivity.class);
        intent.putExtra("switch_stats", switchOn);
        startActivity(intent);
    }

    public void launchCarImageActivity(View view) {
        Intent intent = new Intent(this, CarImageActivity.class);
        intent.putExtra("switch_stats", switchOn);
        startActivity(intent);
    }

    public void launchHintsActivity(View view) {
        Intent intent = new Intent(this, CarHintActivity.class);
        intent.putExtra("switch_stats", switchOn);
        startActivity(intent);
    }

    public void launchAdvancedLevelActivity(View view) {
        Intent intent = new Intent(this, AdvancedActivity.class);
        intent.putExtra("switch_stats", switchOn);
        startActivity(intent);
    }
}