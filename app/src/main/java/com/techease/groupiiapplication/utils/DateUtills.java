package com.techease.groupiiapplication.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtills {


    public static final String DATE_FORMAT_1 = "yyyy-mm-dd";
    public static final String DATE_FORMAT_3 = "yyyy-m-dd";
    public static final String DATE_FORMAT_2 = "MM/dd/yyyy";


    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    public static int mYear, mMonth, mDay, mHour, mMinute;


    public static void GetDatePickerDialog(EditText tvSetDate, Context context) {

        int mYear, mMonth, mDay;
        String date = AppRepository.mTripStartDate(context);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = df.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            System.out.println(c.getTimeInMillis());


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

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Please first select start date", Toast.LENGTH_SHORT).show();

        }


    }

    public static void GetStartDatePickerDialog(EditText tvSetDate, EditText tvNext7Day, EditText tvNext14Days, Context context) {

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

                        ////set trip start date
                        AppRepository.mPutValue(context).putString("trip_start_date", selectedDate).commit();

                        try {
                            GetDatePickerNext7Days(tvNext7Day, selectedDate);
                            GetDatePickerNext14Days(tvNext14Days, selectedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Please first select start date", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static void GetDatePickerNext7Days(EditText tvSetDate, String dt) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 7);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date

        tvSetDate.setText(dt);

    }

    public static void GetDatePickerNext14Days(EditText tvSetDate, String dt) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, -7);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date

        tvSetDate.setText(dt);

    }


    public static String getCurrentDate(String DATE_FORMAT) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    public static void GetTimeDialog(TextView tvTime, Context context) {


        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        tvTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public static String timeFormated(String time) {
        String inputPattern = "HH:mm:ss";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    public static String getTripDetailDayleft(String inputDate) {


        try {
            //Dates to compare
            String CurrentDate = getCurrentDate();
            String FinalDate = inputDate;

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

            //Setting dates
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            String dayDifference = Long.toString(differenceDates);


            return dayDifference;

        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }
        return "";
    }

    public static String getDateFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("LLLL dd,yyyy");
            Date date = null;
            date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018
            Log.d("zma date", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }


    public static String getDateActivityDayFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(DATE_FORMAT_3);
            SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT_1);
            Date date = null;
            date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018
            Log.d("zma date", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getEditDateFormate(String timestamp) {
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT_1);
//            Date date = null;
//            date = inputFormat.parse(timestamp);
//            String formattedDate = outputFormat.format(date);
//            System.out.println(formattedDate); // prints 10-04-2018
//            Log.d("zma date", formattedDate);
//            return formattedDate;
//        } catch (Exception e) {
//        }


        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(timestamp, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        System.out.println(formattedDate); // prints 10-04-2018
        return formattedDate;
    }


    public static String getChatDateFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+5"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018

            Log.d("zma date", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }

    public static String getPhotoGalleryDateFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:m:ss.SSS'Z'");
//            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+5"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("LLLL-dd-yyyy hh:mm a");
            Date date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018

            Log.d("zma date", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }

    public static String setDateAddDayTripFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+5"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy,mm,dd");
            Date date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018
            Log.d("zma date", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }


    public static String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            Log.d("nowTime", nowTime + "now and pass time   " + pasTime);


            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes " + suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours " + suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day + " Days " + suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }


    public static String changeDateFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+5"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018

            Log.d("zma date", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }

    public static String changeDateTripStartDateFormate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+5"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(timestamp);
            String formattedDate = outputFormat.format(date);
            System.out.println(formattedDate); // prints 10-04-2018

            Log.d("zmaformatedate", formattedDate);
            return formattedDate;
        } catch (Exception e) {
        }
        return "";
    }


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEK_MILLIS = 7 * DAY_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else if (diff < 7 * DAY_MILLIS) {
            return diff / DAY_MILLIS + " days ago";
        } else if (diff < 2 * WEEK_MILLIS) {
            return "a week ago";
        } else {
            return diff / WEEK_MILLIS + " weeks ago";
        }
    }


    public static String getPreviousDate(String inputDate) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = dateFormat.parse(inputDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1);
        String yesterdayAsString = dateFormat.format(calendar.getTime());

        return yesterdayAsString;
    }


    public static String getFormatedDate(String inputDate) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = dateFormat.parse(inputDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String yesterdayAsString = dateFormat.format(calendar.getTime());

        return yesterdayAsString;
    }


    public static String getNextMonthDate(String inputDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = dateFormat.parse(inputDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        String yesterdayAsString = dateFormat.format(calendar.getTime());

        return yesterdayAsString;
    }


    public static void GetDatePickerNextOneMonth(EditText tvSetDate, String dt) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 30);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date

        tvSetDate.setText(dt);

    }

}
