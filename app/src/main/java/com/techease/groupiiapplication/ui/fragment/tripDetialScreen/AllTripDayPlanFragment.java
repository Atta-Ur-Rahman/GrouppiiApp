package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.AllTripDayAdapter;
import com.techease.groupiiapplication.dataModel.tripDetial.getAllTripDay.AllTripDayDataModel;
import com.techease.groupiiapplication.dataModel.tripDetial.getAllTripDay.AllTripDayResponse;
import com.techease.groupiiapplication.horizontalCalender.HorizontalCalendar;
import com.techease.groupiiapplication.horizontalCalender.utils.HorizontalCalendarListener;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTripDayPlanFragment extends Fragment implements View.OnClickListener {

    int pos = 0;

    @BindView(R.id.rvAllTripDay)
    RecyclerView rvAllTripDay;

    @BindView(R.id.tvAllDayPlan)
    TextView tvAllDayPlan;
    @BindView(R.id.tvTripDayNotFound)
    TextView tvTripDayNotFound;

    @BindView(R.id.datePickerTimeline)
    DatePickerTimeline datePickerTimeline;

    public static TextView tvTripNotFound;
    Dialog dialog;


    String strDate, strNewDate;

    LinearLayoutManager linearLayoutManager;
    public static AllTripDayAdapter allTripDayAdapter;
    public static List<AllTripDayDataModel> addTripDataModels = new ArrayList<>();

    public static AllTripDayPlanFragment newInstance() {
        return new AllTripDayPlanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_trip_day_plan, container, false);
        ButterKnife.bind(this, view);
        dialog = AlertUtils.createProgressDialog(getActivity());
        tvTripNotFound = tvTripDayNotFound;
//        dialog = AlertUtils.createProgressDialog(getActivity());
        ApiCallAllTirp(AppRepository.mTripIDForUpdation(getActivity()));
        initAdapter();
        CustomDatePicker();


        Calendar startDate = Calendar.getInstance();


        String strStartDate = AppRepository.mTripStartDate(getActivity());
        String strEndDate = AppRepository.mTripEndDate(getActivity());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int mYear = 0, mMonth = 0, mDay = 0;

            Date dt = df.parse(strStartDate);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH) - 5;

            startDate.set(Calendar.DAY_OF_MONTH, mDay);
            startDate.set(Calendar.MONTH, mMonth);
            startDate.set(Calendar.YEAR, mYear);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar endDate = Calendar.getInstance();
        try {
            int mYear = 0, mMonth = 0, mDay = 0;
            Date dt = df.parse(strEndDate);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH) + 5;

            endDate.set(Calendar.DAY_OF_MONTH, mDay);
            endDate.set(Calendar.MONTH, mMonth);
            endDate.set(Calendar.YEAR, mYear);


        } catch (ParseException e) {
            e.printStackTrace();
        }



        /* end after 1 month from now */
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .textSize(14f, 24f, 14f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, getResources().getColor(R.color.purple_500))
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar calendar, int position) {


//                calendar.add(Calendar.DATE, -1);
                calendar.add(Calendar.DAY_OF_MONTH, 1);

                Date date = calendar.getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String inActiveDate = null;
                inActiveDate = format1.format(date);

                Log.d("zmadatecalender", inActiveDate);

                try {
                    ApiCallAllTirpGetByDate(AppRepository.mTripIDForUpdation(getActivity()), inActiveDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        });


        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void CustomDatePicker() {
        // Set a Start date (Default, 1 Jan 1970)
        String year = DateUtills.getCurrentDate("yyyy");
        String month = DateUtills.getCurrentDate("MM");
        String day = DateUtills.getCurrentDate("dd");


        int mYear = 0, mMonth = 0, mDay = 0;

        String date = AppRepository.mTripStartDate(getActivity());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = df.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH) - 3;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        datePickerTimeline.setInitialDate(mYear, mMonth, mDay);


        // Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                // Do Something
                strDate = year + "-" + month + 1 + "-" + day;

                try {
                    ApiCallAllTirpGetByDate(AppRepository.mTripIDForUpdation(getActivity()), "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
//                strDate = year + "-" + month + "-" + day;
                try {
                    ApiCallAllTirpGetByDate(AppRepository.mTripIDForUpdation(getActivity()), "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void ApiCallAllTirp(String tripId) {
        Log.d("zmadate", tripId + "");

        addTripDataModels.clear();
        Call<AllTripDayResponse> allTripDayResponseCall = BaseNetworking.ApiInterface().getAllTripDayResponse("tripdays/getall/" + tripId);
        allTripDayResponseCall.enqueue(new Callback<AllTripDayResponse>() {
            @Override
            public void onResponse(Call<AllTripDayResponse> call, Response<AllTripDayResponse> response) {
                if (response.isSuccessful()) {
                    addTripDataModels.addAll(response.body().getData());
                    Collections.reverse(addTripDataModels);
                    allTripDayAdapter.notifyDataSetChanged();

                    if (addTripDataModels.size() == 0) {
                        tvTripNotFound.setVisibility(View.VISIBLE);
                    } else {
                        tvTripNotFound.setVisibility(View.GONE);
                    }

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


    public void ApiCallAllTirpGetByDate(String userId, String strDate) throws ParseException {

        dialog.show();
//        Log.d("zmadate", DateUtills.getDateActivityDayFormate(strDate));


        addTripDataModels.clear();
        Call<AllTripDayResponse> allTripDayResponseCall = BaseNetworking.ApiInterface().getTripByDate(strDate, userId);
        allTripDayResponseCall.enqueue(new Callback<AllTripDayResponse>() {
            @Override
            public void onResponse(Call<AllTripDayResponse> call, Response<AllTripDayResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    addTripDataModels.addAll(response.body().getData());
                    Collections.reverse(addTripDataModels);
                    allTripDayAdapter.notifyDataSetChanged();
                    if (addTripDataModels.size() == 0) {
                        tvTripDayNotFound.setVisibility(View.VISIBLE);
                    } else {
                        tvTripDayNotFound.setVisibility(View.GONE);
                    }
                } else {
                    dialog.dismiss();
                }

                Log.d("zma trip day", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<AllTripDayResponse> call, Throwable t) {

                dialog.dismiss();
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


    @OnClick({R.id.tvAllDayPlan})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvAllDayPlan:
                ApiCallAllTirp(AppRepository.mTripIDForUpdation(getActivity()));
                break;
        }
    }
}