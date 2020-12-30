package com.techease.groupiiapplication.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatePickerClass {


    public static final String DATE_FORMAT_1= "yyyy-mm-dd";


    public static void GetDatePickerDialog(TextView tvSetDate, Context context) {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EE, MMM dd");

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String selectedDate = simpledateformat.format(newDate.getTime());

                        tvSetDate.setText(selectedDate);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }



    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }



}
