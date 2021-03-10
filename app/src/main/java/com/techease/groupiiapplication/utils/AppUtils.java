package com.techease.groupiiapplication.utils;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.os.Build.VERSION_CODES.R;



public class AppUtils {


    public static final String M_CONNECTION_CHANNEL_ID = "1";
    public static final String M_SERVICE_CHANNEL_ID = "2";
    private static final String CHANNEL_ID = "XMPP";


    public static int IMAGE = 1;
    public static int AUDIO = 0;

    public static final String TEMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final String LOCAL_TIME_FORMAT = "EE MMM dd hh:mm:ss ZZZZ yyyy";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String APP_NAME = "Darewro";
    public static final String HOUR = "HH";
    public static final String MIN = "mm";
    public static final String DAY = "dd";
    public static final String MONTH = "MM";
    public static final String YEAR = "yyyy";
    public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss.S";
    public static final String FORMAT2 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT3 = "yyyy-MM-dd";
    public static final String FORMAT4 = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String FORMAT5 = "hh:mm a";
    public static final String FORMAT6 = "HH:mm";
    public static final String FORMAT7 = "HH";
    public static final String FORMAT8 = "EEE";
    public static final String FORMAT9 = "dd, MMMM yyyy";
    public static final String FORMAT10 = "MMM dd, yyyy"; //   Nov 12, 2016
    public static final String FORMAT11 = "MM/dd/yyyy hh:mm a"; //    08/20/2018 6:13 PM
    public static final String FORMAT12 = "dd/MM/yyyy hh:mm a"; //    20/08/2018 6:13 PM
    public static final String FORMAT13 = "dd/MM/yyyy hh:mm:ss a"; //    20/08/2018 6:13 PM
    public static final String FORMAT14 = "yyyy-MM-dd HH:mm:ss"; //    20/08/2018 6:13
    public static final String FORMAT15 = "dd-MM-yyyy"; //    20/08/2018
    public static final String FORMAT16 = "dd-MM-yyyy hh:mm a"; //    20/08/2018 6:13 PM
    public static final int MY_IGNORE_OPTIMIZATION_REQUEST = 5;
    public static final int STARTING = 0;
    public static final int ENDING = 1;
    public static final int ENDED = 2;
    public static final int INVALID = 3;
    public static String USERID;
    private static String TAG = "AppUtils";
    private static Toast toast;

    /*public static String getDeviceId(Context context) {

        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;
    }*/

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static String getDay(String date) {
        Calendar calendar = Calendar.getInstance();

        String[] datetime = date.split(" ");
        String[] ymd = datetime[0].split("-");
        Date currentdate = calendar.getTime();
        int year = Integer.parseInt(ymd[0]) - 1900;
        int month = Integer.parseInt(ymd[1]) - 1;
        int day = Integer.parseInt(ymd[2]);
        if (currentdate.getYear() == year && currentdate.getMonth() == month && currentdate.getDate() == day) {
            return "TODAY";
        }

        Date newdate = new Date();
        newdate.setYear(year);
        newdate.setMonth(month);
        newdate.setDate(day);
        Log.i("date1 ", String.valueOf(newdate.getTime()));
        calendar.setTime(newdate);
        Log.i("date2 ", String.valueOf(calendar.getTime()));

        String[] days = new String[]{"", "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

        String d = days[calendar.get(Calendar.DAY_OF_WEEK)];
        return d;
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
            ComponentName componentName =  service.service;
            String serviceName = componentName.getClassName();
            if (serviceName.equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }









    public static void playsound(Context context) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mp = MediaPlayer.create(context, alarmSound);
        mp.start();
    }

    public static String printDifference(Date startDate, Date endDate, int status) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = (long) (daysInMilli * 365.24 / 12);

        long elapsedMonths = different / monthsInMilli;
        different = different % monthsInMilli;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        String diff = "";

        if (elapsedMonths > 0) {
            diff += String.format("%d Mo, ", elapsedMonths);
        }
        if (elapsedDays > 0) {
            diff += String.format("%d Days, ", elapsedDays);
        }
        if (elapsedHours > 0) {
            diff += String.format("%d Hrs, ", elapsedHours);
        }
        if (elapsedMinutes > 0) {
            diff += String.format("%d Min, ", elapsedMinutes);
        }

        if ((status == STARTING || status == ENDING) && elapsedSeconds > 0) {
            diff += String.format("%d Sec, ", elapsedSeconds);
        }
        if (diff.length() > 0) {
            diff = diff.substring(0, diff.length() - 2);
        }

//        String diff = String.format("%d mo, %d days, %d hrs, %d min"/*, %d sec %n*/, elapsedMonths,
//                elapsedDays, elapsedHours, elapsedMinutes/*, elapsedSeconds*/);
        System.out.printf("%d months, %d days, %d hours, %d minutes, %d seconds%n", elapsedMonths, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedMonths > 12 ? elapsedMonths / 12 + " Years" : diff;

    }


    public static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
    public static String generateNewMessageId() {
        return String.format("%05d-%04d-%04d-%04d%04d%04d", new Random().nextInt(100000), new Random().nextInt(10000), new Random().nextInt(10000), new Random().nextInt(10000), new Random().nextInt(10000), new Random().nextInt(10000));
    }
    public static String getPreparationCountDownTime(String startDate, String endDate) {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        if (getDateFromString(endDate, FORMAT1).compareTo(getDateFromString(startDate, FORMAT1)) >= 0) {
            if (currentDate.compareTo(getDateFromString(startDate, FORMAT1)) < 0) {
                return "Cooking will be started in " + printDifference(currentDate, getDateFromString(startDate, FORMAT1), STARTING);

            } else if (currentDate.compareTo(getDateFromString(startDate, FORMAT1)) > 0 && currentDate.compareTo(getDateFromString(endDate, FORMAT1)) < 0) {
                return "Food will be ready in <br> <font color='red'> <b>" + printDifference(currentDate, getDateFromString(endDate, FORMAT1), ENDING) + "</b> </font>";
            } else {
                return "Food is ready ";//+ printDifference(getDateFromString(endDate, FORMAT1), currentDate, ENDED) + " Ago";
            }
        }

        return "Food is ready ";//"StartDate is after End Date \n" + printDifference(getDateFromString(startDate, FORMAT1), getDateFromString(endDate, FORMAT1), INVALID);


//        return getConvertedDateFromOneFormatToOther(startDate, AppUtils.FORMAT1, AppUtils.FORMAT10) + " - " + getConvertedDateFromOneFormatToOther(endDate, AppUtils.FORMAT1, AppUtils.FORMAT10);
    }

    public static String getConvertedDateFromOneFormatToOther(String date, String currentFormat, String requiredFormat) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(currentFormat);
            SimpleDateFormat sdf2 = new SimpleDateFormat(requiredFormat);
            Date c = null;
            c = sdf.parse(date);
            String date1 = sdf2.format(c);
//            System.out.println(date1);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Date getDateFromString(String date, String currentFormat) {
        if (date.contains("T")) {
            date = date.replace('T', ' ');
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(currentFormat);
            Date c = null;
            c = sdf.parse(date);
            return c;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Date getDateFromString2(String date) {
        if (date.contains("T")) {
            date = date.replace('T', ' ');
        }
        try {

            SimpleDateFormat sdf = new SimpleDateFormat();
            if (DATE_FORMAT.contains(".")) {
                sdf = new SimpleDateFormat(FORMAT1);
            } else {
                sdf = new SimpleDateFormat(FORMAT14);
            }
            Date c = null;
            c = sdf.parse(date);
            return c;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getStringFromDate(Date date, String currentFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(currentFormat);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



    public static int getScreenWidth(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int widthPixels = displaymetrics.widthPixels;
        return widthPixels;
    }

    public static String getCurrentTimeStamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String longToDateTime(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        return formatter.format(date);
    }

    public static long getCurrentTimeStampInMilliSeconds() {
        return System.currentTimeMillis();
    }

    public static void deleteFolder(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles())
                deleteFolder(child);
        }
        fileOrDirectory.delete();
//        UpdateAndroidGallery();
    }

    public static String getDefaultTimeZoneDateTime(String datetime) {

        String scheduleTime = datetime;

        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();

        SimpleDateFormat readDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        readDate.setTimeZone(TimeZone.getTimeZone("GMT")); // missing line
        Date date2 = null;
        try {
            date2 = readDate.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat writeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        writeDate.setTimeZone(new SimpleTimeZone(mGMTOffset, mTimeZone.getID()));
        scheduleTime = writeDate.format(date2);

        Log.i("Date2 = ", date2.toString());
        Log.i("scheduledTime = ", scheduleTime);

        return scheduleTime;
    }

    public static String getDateWithMonthInInteger(String date) {

        if (date == null || date.isEmpty()) {
            return "";
        }
        Log.e("date = ", date + ">>");
        date = date.replace(",", "");
        date = date.replace(".", "");
        date = date.replace(" ", "-");
        String datearray[] = date.split("-");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("MMM").parse(datearray[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int monthInt = cal.get(Calendar.MONTH) + 1;
        String day = Integer.parseInt(datearray[0]) < 10 ? "0" + datearray[0] : datearray[0];
        String mon = monthInt < 10 ? "0" + String.valueOf(monthInt) : String.valueOf(monthInt);
//        String newdate = day + "-" + mon + "-" + datearray[2];
        String newdate = datearray[2] + "-" + mon + "-" + day;

        return newdate;
    }

    public static String getCurrentTimeStampNoSeconds() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getCurrentTimeOfDay() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getCurrentTimeOnlyWithSeconds() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getCurrentDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getCurrentTimeStamp(String outputFormat) {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat(outputFormat);

            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getSha1Hex(String clearString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    // Coverts a bitmap into a base64 image.
    public static String getBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String getDeviceDetails() {

        return (Build.MANUFACTURER + " -> " + Build.MODEL + " -> "
                + Build.VERSION.RELEASE);
    }

    public static boolean checkNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void apiSpecificTasks(Activity activity) {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {

        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean checkEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean checkPassword(String password) {
        if (password.length() >= 8) {
            for (int x = 0; x < password.length(); x++) {
                if (Character.isDigit(password.charAt(x))) {
                    return true;
                }

            }
            return false;
        } else {
            return false;
        }
    }


    public static void justify(final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }



    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }



    public static void setText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setText("--");
        } else {
            textView.setText(text);
        }
    }



    public static String readStream(InputStream stream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(stream));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void writeFile(byte[] data, File file) throws IOException {

        BufferedOutputStream bos = null;

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static byte[] getFileContents(File file) {

        byte[] data = null;
        try {
            RandomAccessFile f = new RandomAccessFile(file.getPath(), "r");
            data = new byte[(int) f.length()];
            f.read(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getFilePathFromUri(Activity activity, Uri uri) {
        String path = "";
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return path;
    }

    public static void viewImage(Context context, String imagePath) {
        try {
            File file = new File(imagePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            context.startActivity(intent);
        } catch (Throwable t) {

        }
    }

    public static boolean checkExternalStorageState() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }



    public static List<String> getCameraImages(Context context) {

        String sort = MediaStore.Images.ImageColumns.DATE_MODIFIED/*_TAKEN*//*ADDED*/ + " DESC";
        final String[] projection = {MediaStore.Images.Media.DATA};
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sort);
        ArrayList<String> result = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static void UpdateAndroidGallery(Context context) {
        File file = new File(getAppDirectory(context).getPath());
        file.delete();
        if (file.exists()) {
            try {
                file.getCanonicalFile().delete();
                context.getApplicationContext().deleteFile(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentUtcTime() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    public static Date getCurrentDateTime() {

        String currentUtcTime = getCurrentUtcTime();
        DateFormat inputFormatter1 = new SimpleDateFormat(DATE_FORMAT);
        Date date1 = null;
        try {
            date1 = inputFormatter1.parse(currentUtcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static File getAppDirectory(Context context) {

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, /*"Pictures/"+*/APP_NAME);
        if (!folder.exists()) {
            Log.d("Directory", "mkdirs():" + folder.mkdirs());
        }
        return folder;
    }

    public static byte[] getImageBytes(String path) {
        File fileName = new File(path);
        byte[] bytes;
        byte[] buffer = new byte[512];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            InputStream inputStream = new FileInputStream(fileName);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return bytes;
    }

    public static byte[] getJpegConvertedImageBytes(String path, String type) {
        File imagefile = new File(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        byte[] buffer = new byte[512];
        int bytesRead;
        if (type.equalsIgnoreCase("img")) {
            Log.i("image", "image");
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(imagefile);
                Bitmap bm = BitmapFactory.decodeStream(fis);
                bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("vid")) {
            Log.i("video", "video");
            try {
                InputStream inputStream = new FileInputStream(imagefile);
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return bytes;
    }

    public static String getFormattedLocalTimeFromUtc(String utcTimeStamp, String outputFormat) {

        String formattedTime = null;
        if (!TextUtils.isEmpty(utcTimeStamp)) {

            String localTime = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date localDateTime = null;
            try {
                localDateTime = sdf.parse(utcTimeStamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat outputDateFormat1 = new SimpleDateFormat(outputFormat);
            localTime = outputDateFormat1.format(localDateTime);

            DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            try {
                date = inputDateFormat.parse(localTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            formattedTime = outputDateFormat.format(date);
            Log.d("TIME", formattedTime);
        }
        return formattedTime;
    }

    @Nullable
    public static String formatDateTime(String dateTime) {
        try {
            if (!TextUtils.isEmpty(dateTime) && dateTime.contains(" ")) {
                String date = dateTime.split(" ")[0];
                String time = dateTime.split(" ")[1];

                if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(time)) {
                    if (time.contains(":")) {
                        try {
                            int hr = Integer.parseInt(time.split(":")[0].trim());
                            int min = Integer.parseInt(time.split(":")[1].trim());
                            DecimalFormat df = new DecimalFormat("00");
                            time = String.format("%s:%s", df.format(hr), df.format(min));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                dateTime = String.format("%s %s", date, time);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return dateTime;
    }

    public static float dp2px(Resources resources, float dp) {

        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float dp2px(Resources resources, int dimenId) {

        final float scale = resources.getDisplayMetrics().density;
        return resources.getDimension(dimenId) * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {

        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static float pixelsToSp(Context context, float px) {

        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static void log(Class a, String msg) {
        Log.e(" >>> " + a.getName(), msg);
    }

    public static void log(String tag, String msg) {
        Log.e(" >>> " + tag, msg);
    }

    public static void loadWithGlide(Context c, String imgPath, ImageView iv) {
        if (TextUtils.isEmpty(imgPath)) {
            log(TAG, "loadWithGlide: imgPath is null!");
            return;
        }
        try {
            RequestOptions ro = new RequestOptions();
            ro.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            Glide.with(c).applyDefaultRequestOptions(ro).load(imgPath).into(iv);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static boolean compareDates(String oldDate, String compareDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Date1 = null;
        try {
            Date1 = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date Date2 = null;
        try {
            Date2 = sdf.parse(compareDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Date2.after(Date1)) {
            return true;
        }
        return false;
    }

    public static int sortDates(String oldDate, String compareDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Date1 = null;
        try {
            Date1 = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date Date2 = null;
        try {
            Date2 = sdf.parse(compareDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Date2.after(Date1)) {
            return 1;
        } else if (Date1.after(Date2)) {
            return -1;
        }
        return 0;
    }

    public static String getUTF8EncodedString(String text) {
        try {
            String str = Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
            Log.i("text = ", text);
            Log.i("encoded text = ", str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String getUTF8DecodedString(String text) {
        try {
            String str = new String(Base64.decode(text.getBytes(), Base64.DEFAULT));
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }




    public static void setUnderligned(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public static void setUnderligned(EditText editText) {
        editText.setPaintFlags(editText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public static void setUnderligned(Button button) {
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public static void showView(TextView view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void hideView(TextView view) {
        view.setVisibility(View.GONE);
    }

    public static String getDate(String dateTimePlacement) {
        if (dateTimePlacement.contains("T")) {
            dateTimePlacement = dateTimePlacement.replace('T', ' ');
        }
        try {
            SimpleDateFormat dateFormat = null;
            if (dateTimePlacement.contains(".")) {
                dateFormat = new SimpleDateFormat(FORMAT1);
            } else {
                dateFormat = new SimpleDateFormat(FORMAT14);
            }
            SimpleDateFormat sdf2 = new SimpleDateFormat(FORMAT9);
            Date c = null;
            c = dateFormat.parse(dateTimePlacement);
            String date1 = sdf2.format(c);
//            System.out.println(date1);
            return date1;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getTime(String dateTimePlacement) {
        if (dateTimePlacement.contains("T")) {
            dateTimePlacement = dateTimePlacement.replace('T', ' ');
        }
        try {
            SimpleDateFormat dateFormat = null;
            if (dateTimePlacement.contains(".")) {
                dateFormat = new SimpleDateFormat(FORMAT1);
            } else {
                dateFormat = new SimpleDateFormat(FORMAT14);
            }
            SimpleDateFormat sdf2 = new SimpleDateFormat(FORMAT5);
            Date c = null;
            c = dateFormat.parse(dateTimePlacement);
            String date1 = sdf2.format(c);
//            System.out.println(date1);
            return date1;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getFormattedPhoneNumber(String msisdn) {
        String phone = msisdn;

        if (!TextUtils.isEmpty(msisdn)) {
            if (msisdn.length() == 13 && msisdn.startsWith("+92")) {
                phone = msisdn;
            } else if (msisdn.length() == 14 && msisdn.startsWith("0092")) {
                phone = msisdn.replaceFirst("0092", "+92");
            } else if (msisdn.length() == 11 && msisdn.startsWith("03")) {
                phone = msisdn.replaceFirst("0", "+92");
            }
        }
        return phone;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
     /*   if (drawable instanceof BitmapDrawable) {
            ((BitmapDrawable)drawable).setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            return ((BitmapDrawable) drawable).getBitmap();
        }
*/
        int width = drawable.getIntrinsicWidth() * 2;
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight() * 2;
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth() / 2, canvas.getHeight());
        drawable.draw(canvas);

        drawable.setBounds(canvas.getWidth() / 2, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static void makePhoneCall(AppCompatActivity context, String phone) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

}


