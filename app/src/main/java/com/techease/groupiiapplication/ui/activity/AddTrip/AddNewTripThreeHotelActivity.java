package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.HotelAdapter;
import com.techease.groupiiapplication.dataModel.hotel.HotelDataModel;
import com.techease.groupiiapplication.dataModel.hotel.HotelResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;

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
    List<HotelDataModel> hotelDataModels = new ArrayList<>();
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;
    @BindView(R.id.btnReverse)
    Button btnReverse;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_three);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initAdapter();
        apiCallGetTripDetail();

    }

    private void apiCallGetTripDetail() {
        dialog.show();
        Call<HotelResponse> hotelResponseCall = BaseNetworking.ApiInterfaceForHotel("9TdgK5GOeFSRUB8sxYRPIfbSAeKX").getNearHotel("PEW", "52.5238", "13.3835");
        hotelResponseCall.enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                if (response.isSuccessful()) {

                    hotelDataModels.addAll(response.body().getData());
                    hotelAdapter.notifyDataSetChanged();
                    Log.d("zma hotel",String.valueOf(response.message()));
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                dialog.dismiss();
                Log.d("zma hotel",String.valueOf(t.getMessage()));

            }
        });

//        Call<TripDetailResponse> call = BaseNetworking.ApiInterface().getTripDetail(AppRepository.mUserID(this));
//        call.enqueue(new Callback<TripDetailResponse>() {
//            @Override
//            public void onResponse(Call<TripDetailResponse> call, Response<TripDetailResponse> response) {
//                if (response.isSuccessful()) {
//                    dialog.dismiss();
//                    tripDetailDataModels.addAll(response.body().getData());
//                    Log.d("zma response",String.valueOf(response.message()+tripDetailDataModels.size()));
//
//                    tripDetailAdapter.notifyDataSetChanged();
//                } else {
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TripDetailResponse> call, Throwable t) {
//                dialog.dismiss();
//
//                Log.d("zma response",String.valueOf(t.getMessage() ));
//
//            }
//        });
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
                finish();
                startActivity(new Intent(this, NewTripStepFourPaymentActivity.class));
                break;

        }
    }
}