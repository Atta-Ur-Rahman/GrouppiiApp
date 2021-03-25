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
import com.techease.groupiiapplication.utils.DatePickerClass;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

    @BindView(R.id.datePickerTimeline)
    DatePickerTimeline datePickerTimeline;


    //    Dialog dialog;
    String strDate;


    LinearLayoutManager linearLayoutManager;
    public static AllTripDayAdapter allTripDayAdapter;
    public static List<AllTripDayDataModel> addTripDataModels = new ArrayList<>();


    public static AllTripDayFragment newInstance() {
        return new AllTripDayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_trip_day, container, false);
        ButterKnife.bind(this, view);
//        dialog = AlertUtils.createProgressDialog(getActivity());

        ApiCallAllTirp(AppRepository.mTripId(getActivity()));
        initAdapter();

        CustomDatePicker();

        return view;
    }

    private void CustomDatePicker() {
// Set a Start date (Default, 1 Jan 1970)

        String year = DatePickerClass.getCurrentDate("yyyy");
        String month = DatePickerClass.getCurrentDate("MM");
        String day = DatePickerClass.getCurrentDate("dd");

        datePickerTimeline.setInitialDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                // Do Something

                strDate = year + "-" + month + "-" + day;
                ApiCallAllTirpGetByDate(AppRepository.mTripId(getActivity()));
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

                // Do Something
            }
        });

// Disable date
        Date[] dates = {Calendar.getInstance().getTime()};
        datePickerTimeline.deactivateDates(dates);

    }

    public static void ApiCallAllTirp(String tripId) {

        addTripDataModels.clear();
        Call<AllTripDayResponse> allTripDayResponseCall = BaseNetworking.ApiInterface().getAllTripDayResponse("tripdays/getall/" + tripId);
        allTripDayResponseCall.enqueue(new Callback<AllTripDayResponse>() {
            @Override
            public void onResponse(Call<AllTripDayResponse> call, Response<AllTripDayResponse> response) {
                if (response.isSuccessful()) {
                    addTripDataModels.addAll(response.body().getData());
                    Collections.reverse(addTripDataModels);
                    allTripDayAdapter.notifyDataSetChanged();

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


    public void ApiCallAllTirpGetByDate(String userId) {

        addTripDataModels.clear();
        Call<AllTripDayResponse> allTripDayResponseCall = BaseNetworking.ApiInterface().getTripByDate(strDate, userId);
        allTripDayResponseCall.enqueue(new Callback<AllTripDayResponse>() {
            @Override
            public void onResponse(Call<AllTripDayResponse> call, Response<AllTripDayResponse> response) {
                if (response.isSuccessful()) {
//                    dialog.dismiss();
                    addTripDataModels.addAll(response.body().getData());
                    Collections.reverse(addTripDataModels);
                    allTripDayAdapter.notifyDataSetChanged();

                    if (addTripDataModels.size() == 0) {
                        tvTripDayNotFound.setVisibility(View.VISIBLE);
                    } else {
                        tvTripDayNotFound.setVisibility(View.GONE);
                    }
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
        rvAllTripDay.setHasFixedSize(true);
//        rvAllTripDay.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        rvAllTripDay.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvAllTripDay.setAdapter(allTripDayAdapter);
        allTripDayAdapter.notifyDataSetChanged();


    }
}