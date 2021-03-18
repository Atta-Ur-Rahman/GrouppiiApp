package com.techease.groupiiapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppRepository {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String mConfig = "com.techease.newApp";


    public static SharedPreferences mGetValue(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static SharedPreferences.Editor mPutValue(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return editor = sharedPreferences.edit();
    }


    public static String mPasswordToken(Context context) {
        return mGetValue(context).getString("password_token", "");
    }


    public static String mAPIToken(Context context) {
        return mGetValue(context).getString("auth_token", "");
    }

    public static String mTripId(Context context) {
        return mGetValue(context).getString("tripID", "");
    }

    public static String mUrl(Context context) {
        return mGetValue(context).getString("url", "");
    }


    public static String mLat(Context context) {
        return mGetValue(context).getString("lat", "");
    }

    public static String mLng(Context context) {
        return mGetValue(context).getString("lng", "");
    }

    public static int mUserID(Context context) {
        return mGetValue(context).getInt("userID", 0);
    }

    public static String mUserProfileImage(Context context) {
        return String.valueOf(mGetValue(context).getString("mProfilePicture", null));
    }


    public static String mUserName(Context context) {
        return String.valueOf(mGetValue(context).getString("mUserName", null));
    }

    public static String mPhoneNumber(Context context) {
        return String.valueOf(mGetValue(context).getString("mPhoneNumber", null));
    }

    public static String mEmail(Context context) {
        return String.valueOf(mGetValue(context).getString("mUserEmail", null));
    }

    public static String mDeviceToken(Context context) {
        return mGetValue(context).getString("device_token", "");
    }

    public static String mRestaurantId(Context context) {
        return mGetValue(context).getString("restaurant_id", "");
    }


    public static boolean isLoggedIn(Context context) {
        return mGetValue(context).getBoolean("loggedIn", false);

    }

    public static boolean mOneTimeStartTimer(Context context) {
        return mGetValue(context).getBoolean("one_time_start_timer", true);

    }


    public static String mDetailLat(Context context) {
        return mGetValue(context).getString("detail_lat", "");
    }

    public static String mDetailLng(Context context) {
        return mGetValue(context).getString("detail_lng", "");
    }

    public static String mDetailRestaurantName(Context context) {
        return mGetValue(context).getString("detail_restaurant_name", "");
    }


}
