
package com.techease.groupiiapplication.dataModel.loginSignup.signUp;

import com.google.gson.annotations.SerializedName;

public class SignUpDataModel {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("device_type")
    private String mDeviceType;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fcm_token")
    private String mFcmToken;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("notification")
    private Long mNotification;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("usertype")
    private String mUsertype;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        mDeviceType = deviceType;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFcmToken() {
        return mFcmToken;
    }

    public void setFcmToken(String fcmToken) {
        mFcmToken = fcmToken;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getNotification() {
        return mNotification;
    }

    public void setNotification(Long notification) {
        mNotification = notification;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUsertype() {
        return mUsertype;
    }

    public void setUsertype(String usertype) {
        mUsertype = usertype;
    }

}
