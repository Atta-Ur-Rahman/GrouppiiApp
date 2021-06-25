package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

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
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
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

    public static TextView tvTripNotFound;


    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    //    Dialog dialog;
    String strDate, strNewDate;

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
        tvTripNotFound = tvTripDayNotFound;
//        dialog = AlertUtils.createProgressDialog(getActivity());
        ApiCallAllTirp(AppRepository.mTripId(getActivity()));
        initAdapter();
        CustomDatePicker();



        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);


        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);

        Log.d("zmadatecheck", strDate + "    " + endDate);


        // on below line we are setting up our horizontal calendar view and passing id our calendar view to it.
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                // on below line we are adding a range
                // as start date and end date to our calendar.
                .range(startDate, endDate)
                // on below line we are providing a number of dates
                // which will be visible on the screen at a time.
                .datesNumberOnScreen(6)
                // at last we are calling a build method
                // to build our horizontal recycler view.
                .build();
        // on below line we are setting calendar listener to our calendar view.
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // on below line we are printing date
                // in the logcat which is selected.

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(date.getTimeInMillis());

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                strDate = mDay + "-" + mMonth + "-" + mYear;

                strNewDate = mYear + "-" + mMonth + "-" + mDay;


                Log.d("zmadatecalender", "CURRENT DATE " + strDate);
                try {
                    Log.d("zmadatecalender", "CURRENT DATE IS " + DateUtills.getNextMonthDate(DateUtills.getPreviousDate(strDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ApiCallAllTirpGetByDate(AppRepository.mTripId(getActivity()));

                try {

                } catch (Exception ignored) {


                }


            }
        });


        return view;
    }

    private void CustomDatePicker() {
// Set a Start date (Default, 1 Jan 1970)

        String year = DateUtills.getCurrentDate("yyyy");
        String month = DateUtills.getCurrentDate("MM");
        String day = DateUtills.getCurrentDate("dd");

        Log.d("zma date", year + " " + month + " " + day);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);


        datePickerTimeline.setInitialDate(Integer.parseInt(year), Integer.parseInt(String.valueOf(Integer.parseInt(month) - 1)), Integer.parseInt(day) - 1);
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


    public void ApiCallAllTirpGetByDate(String userId) {
        Toast.makeText(getActivity(), "call", Toast.LENGTH_SHORT).show();
        addTripDataModels.clear();
        Call<AllTripDayResponse> allTripDayResponseCall = BaseNetworking.ApiInterface().getTripByDate(strNewDate, userId);
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