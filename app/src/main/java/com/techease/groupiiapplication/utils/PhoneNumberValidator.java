package com.techease.groupiiapplication.utils;

import android.text.TextUtils;

public class PhoneNumberValidator {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

}
