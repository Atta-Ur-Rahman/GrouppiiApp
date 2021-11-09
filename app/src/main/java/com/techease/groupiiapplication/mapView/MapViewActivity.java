package com.techease.groupiiapplication.mapView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.techease.groupiiapplication.R;

import java.util.Objects;

public class MapViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapsFragment.newInstance())
                    .commitNow();
        }
    }
}