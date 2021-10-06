package com.techease.groupiiapplication.ui.activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.techease.groupiiapplication.MapsFragment;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.fragment.profile.AddCreditCardFragment;

import java.util.Objects;

public class AddCreditCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddCreditCardFragment.newInstance())
                    .commitNow();
        }
    }
}