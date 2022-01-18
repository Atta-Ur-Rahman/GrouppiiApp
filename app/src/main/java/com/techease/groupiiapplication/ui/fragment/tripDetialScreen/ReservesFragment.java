package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.AllReserveAdapter;
import com.techease.groupiiapplication.dataModel.getReserveModel.GetAllReserveResponse;
import com.techease.groupiiapplication.dataModel.getReserveModel.GetReserveDataModel;
import com.techease.groupiiapplication.dataModel.photomodel.DataItem;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservesFragment extends Fragment {


    @BindView(R.id.tvReserveNotFound)
    TextView tvReserveNotFound;
    @BindView(R.id.rvAllReserve)
    RecyclerView rvAllReserve;
    ArrayList<GetReserveDataModel> getReserveDataModels = new ArrayList<>();
    AllReserveAdapter allReserveAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_reserves, container, false);
        ButterKnife.bind(this, parentView);
        initView();
        ApiCallForGellAllReserve(AppRepository.mTripIDForUpdation(getActivity()));

        return parentView;
    }

    private void ApiCallForGellAllReserve(String mTripIDForUpdation) {
        Call<GetAllReserveResponse> getAllReserveResponseCall = BaseNetworking.ApiInterface().getAllReserve(mTripIDForUpdation);
        getAllReserveResponseCall.enqueue(new Callback<GetAllReserveResponse>() {
            @Override
            public void onResponse(Call<GetAllReserveResponse> call, Response<GetAllReserveResponse> response) {
                if (response.isSuccessful()) {
                    getReserveDataModels.clear();
                    Collections.reverse(getReserveDataModels);
                    getReserveDataModels.addAll(response.body().getData());
                    allReserveAdapter.notifyDataSetChanged();
                    if (getReserveDataModels.size() == 0) {
                        tvReserveNotFound.setVisibility(View.VISIBLE);
                    } else {
                        tvReserveNotFound.setVisibility(View.GONE);
                    }
                } else {
                    tvReserveNotFound.setVisibility(View.VISIBLE);
                    try {
                        Toast.makeText(getActivity(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllReserveResponse> call, Throwable t) {

            }
        });
    }

    private void initView() {

        rvAllReserve.setHasFixedSize(true);
        rvAllReserve.setLayoutManager(new LinearLayoutManager(getActivity()));
        allReserveAdapter = new AllReserveAdapter(getActivity(), getReserveDataModels);
        rvAllReserve.setAdapter(allReserveAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.aBooleanGetImageForReserve) {
            Constants.aBooleanGetImageForReserve = false;
            ApiCallForGellAllReserve(AppRepository.mTripIDForUpdation(getActivity()));

        }
    }
}