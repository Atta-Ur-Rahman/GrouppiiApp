package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.fragment.TripFragment;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class NewTripStepFourPaymentActivity extends AppCompatActivity {


    @BindView(R.id.csPayment)
    CircularSeekBar circularSeekBar;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_four_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        circularSeekBar.setEnabled(false);
        ProcessBarAnimation();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripFragment.aBooleanRefreshAllTripApi = true;
                finish();

            }
        });
    }


    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 75, 100);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }
}