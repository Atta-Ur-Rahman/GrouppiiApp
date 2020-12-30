package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.fragment.TripFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class NewTripStepFourPaymentActivity extends AppCompatActivity {


    @BindView(R.id.csPayment)
    CircularSeekBar circularSeekBar;
    @BindView(R.id.btnDone)
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_four_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        circularSeekBar.setEnabled(false);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripFragment.aBooleanRefreshApi = true;
                finish();

            }
        });
    }
}