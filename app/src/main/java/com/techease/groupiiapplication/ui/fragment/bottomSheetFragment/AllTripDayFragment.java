package com.techease.groupiiapplication.ui.fragment.bottomSheetFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.AllTripDayAdapter;
import com.techease.groupiiapplication.dataModel.getAllTripDay.AllTripDayDataModel;
import com.techease.groupiiapplication.dataModel.getAllTripDay.AllTripDayResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTripDayFragment extends Fragment {

    @BindView(R.id.rvAllTripDay)
    RecyclerView rvAllTripDay;

    @BindView(R.id.tvTripDayNotFound)
    TextView tvTripDayNotFound;

//    Dialog dialog;


    LinearLayoutManager linearLayoutManager;
    public static AllTripDayAdapter allTripDayAdapter;
    public static List<AllTripDayDataModel> addTripDataModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_trip_day, container, false);
        ButterKnife.bind(this, view);
//        dialog = AlertUtils.createProgressDialog(getActivity());

        ApiCallAllTirp(AppRepository.mTripId(getActivity()));
        initAdapter();

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);




        return view;
    }

    public static void ApiCallAllTirp(String userId) {

        addTripDataModels.clear();
        Call<AllTripDayResponse> allTripDayResponseCall = BaseNetworking.ApiInterface().getAllTripDayResponse("tripdays/getall/"+"21");
        allTripDayResponseCall.enqueue(new Callback<AllTripDayResponse>() {
            @Override
            public void onResponse(Call<AllTripDayResponse> call, Response<AllTripDayResponse> response) {
                if (response.isSuccessful()) {
//                    dialog.dismiss();
                    addTripDataModels.addAll(response.body().getData());
                    Collections.reverse(addTripDataModels);
                    allTripDayAdapter.notifyDataSetChanged();
                } else {
//                    dialog.dismiss();
                }

                Log.d("zma trip day", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<AllTripDayResponse> call, Throwable t) {

//                dialog.dismiss();
                Log.d("zma trip day error", String.valueOf(t));

            }
        });
    }


    private void initAdapter() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        allTripDayAdapter = new AllTripDayAdapter((getActivity()), addTripDataModels);
        rvAllTripDay.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvAllTripDay.setAdapter(allTripDayAdapter);
        rvAllTripDay.setHasFixedSize(true);
        allTripDayAdapter.notifyDataSetChanged();
        rvAllTripDay.setNestedScrollingEnabled(true);
        rvAllTripDay.setHasFixedSize(true);
        tvTripDayNotFound.setVisibility(View.GONE);
    }
}