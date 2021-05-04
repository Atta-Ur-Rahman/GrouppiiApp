package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GetGalleryPhotoResponse;
import com.techease.groupiiapplication.dataModel.publishTrip.PublishTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.fragment.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTripStepFourPaymentActivity extends AppCompatActivity {


    @BindView(R.id.csPayment)
    CircularSeekBar circularSeekBar;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_four_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        circularSeekBar.setEnabled(false);
        ProcessBarAnimation();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCallPublishTrip();


            }
        });
    }


    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 75, 100);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    private void ApiCallPublishTrip() {
        dialog.show();
        Call<PublishTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().publishTrip("trips/publish/" + AppRepository.mTripId(this));
        getGalleryPhotoResponseCall.enqueue(new Callback<PublishTripResponse>() {
            @Override
            public void onResponse(Call<PublishTripResponse> call, Response<PublishTripResponse> response) {
                if (response.isSuccessful()) {
                    TripFragment.aBooleanRefreshAllTripApi = true;
                    finishAffinity();
                    startActivity(new Intent(NewTripStepFourPaymentActivity.this, HomeActivity.class), ActivityOptions.makeSceneTransitionAnimation(NewTripStepFourPaymentActivity.this).toBundle());

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PublishTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepFourPaymentActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

}