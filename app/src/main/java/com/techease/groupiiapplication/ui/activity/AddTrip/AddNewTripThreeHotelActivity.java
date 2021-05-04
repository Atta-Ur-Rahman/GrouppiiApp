package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.HotelAdapter;
import com.techease.groupiiapplication.dataModel.OgodaHotel.OgodaHotelResponse;
import com.techease.groupiiapplication.dataModel.OgodaHotel.Result;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.AreaDataObject;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.CriteriaDataObject;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.MainHotelObject;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTripThreeHotelActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayoutManager linearLayoutManager;
    HotelAdapter hotelAdapter;
    List<Result> hotelDataModels = new ArrayList<>();
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;
    @BindView(R.id.btnReverse)
    Button btnReverse;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_three);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initAdapter();
        try {
            apiCallGetTripDetail();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        ProcessBarAnimation();

    }

    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 50, 75);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    private void apiCallGetTripDetail() throws JSONException, IOException {
        dialog.show();

        MainHotelObject mainObject = new MainHotelObject();
        AreaDataObject areaDataObject = new AreaDataObject();
        CriteriaDataObject criteriaDataObject = new CriteriaDataObject();

        areaDataObject.setId(26023);
        areaDataObject.setCityId(229);

        criteriaDataObject.setAreaDataObject(areaDataObject);
        criteriaDataObject.setCheckInDate("2021-10-14");
        criteriaDataObject.setCheckOutDate("2021-10-15");

        mainObject.setCriteriaDataObject(criteriaDataObject);


        Call<OgodaHotelResponse> ogodaHotelResponseCall = BaseNetworking.ApiInterfaceForHotel().getAllHotel(mainObject);
        ogodaHotelResponseCall.enqueue(new Callback<OgodaHotelResponse>() {
            @Override
            public void onResponse(Call<OgodaHotelResponse> call, Response<OgodaHotelResponse> response) {

                if (response.isSuccessful()) {
                    hotelDataModels.addAll(response.body().getResults());
                    hotelAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<OgodaHotelResponse> call, Throwable t) {
            }

        });
    }

    private void initAdapter() {

        linearLayoutManager = new LinearLayoutManager(this);
        dialog = AlertUtils.createProgressDialog(this);
        hotelAdapter = new HotelAdapter(this, hotelDataModels);
        rvTripDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvTripDetail.setAdapter(hotelAdapter);
        rvTripDetail.setNestedScrollingEnabled(true);
//        Collections.reverse(tripDetailDataModels);
    }

    @OnClick({R.id.ivBack, R.id.btnReverse})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnReverse:
//                finish();
                startActivity(new Intent(AddNewTripThreeHotelActivity.this, NewTripStepFourPaymentActivity.class), ActivityOptions.makeSceneTransitionAnimation(AddNewTripThreeHotelActivity.this).toBundle());

//                startActivity(new Intent(this, NewTripStepFourPaymentActivity.class));
                break;

        }
    }
}