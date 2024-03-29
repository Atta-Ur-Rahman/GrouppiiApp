package com.techease.groupiiapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppRepository {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String mConfig = "com.techease.grouppii";


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


    public static String mShareTripId(Context context) {
        return mGetValue(context).getString("shareTripId", "linknotavailable");
    }

    public static String mUrl(Context context) {
        return mGetValue(context).getString("url", "");
    }


    public static String mLat(Context context) {
        return mGetValue(context).getString("lat", "null");
    }

    public static String mLng(Context context) {
        return mGetValue(context).getString("lng", "null");
    }

    public static int mUserID(Context context) {
        return mGetValue(context).getInt("userID", 0);
    }

    public static String mUserProfileImage(Context context) {
        return String.valueOf(mGetValue(context).getString("mProfilePicture", null));
    }

    public static String mUserPassword(Context context) {
        return mGetValue(context).getString("mUserPassword", "");
    }


    public static String mUserName(Context context) {
        return String.valueOf(mGetValue(context).getString("mUserName", null));
    }

    public static String mGetFromdate(Context context) {
        return String.valueOf(mGetValue(context).getString("getFromdate", null));
    }

    public static String mTripStartDate(Context context) {
        return String.valueOf(mGetValue(context).getString("trip_start_date", null));
    }

    public static String mTripEndDate(Context context) {
        return String.valueOf(mGetValue(context).getString("trip_end_date", null));
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

    public static boolean isGridView(Context context) {
        return mGetValue(context).getBoolean("aBooleanIsGridView", false);

    }

    public static boolean addPaymentOnStepFour(Context context) {
        return mGetValue(context).getBoolean("add_payment_on_step_four", false);

    }


    public static boolean mOneTimeStartTimer(Context context) {
        return mGetValue(context).getBoolean("one_time_start_timer", true);

    }

    public static boolean mNotification(Context context) {
        return mGetValue(context).getBoolean("notification", true);

    }


    public static boolean mEditDayPlanActivity(Context context) {
        return mGetValue(context).getBoolean("mEditDayPlanActivity", true);

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


    public static String mTripTitleName(Context context) {
        return mGetValue(context).getString("trip_title_name", "");
    }


    public static String mCityName(Context context) {
        return mGetValue(context).getString("cityName", "");
    }

    public static int mCityId(Context context) {
        return mGetValue(context).getInt("cityID", 0);
    }


    public static String mTripIDForUpdation(Context context) {
        return mGetValue(context).getString("tripIDForUpdation", "");
    }


    public static String mCurrencyType(Context context) {
        return mGetValue(context).getString("mCurrencyType", "");
    }


}
