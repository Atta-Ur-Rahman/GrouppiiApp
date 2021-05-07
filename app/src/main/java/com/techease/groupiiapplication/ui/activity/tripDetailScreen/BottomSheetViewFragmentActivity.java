package com.techease.groupiiapplication.ui.activity.tripDetailScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.AllTripDayFragment;

public class BottomSheetViewFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_view_fragment);
        getSupportActionBar().hide();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AllTripDayFragment.newInstance())
                    .commitNow();
        }
    }
}