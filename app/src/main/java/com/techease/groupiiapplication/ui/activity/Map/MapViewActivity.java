package com.techease.groupiiapplication.ui.activity.Map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.techease.groupiiapplication.R;

public class MapViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        getSupportActionBar().hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LocationMapViewFragment.newInstance())
                    .commitNow();
        }
    }
}