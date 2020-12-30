
package com.techease.groupiiapplication.dataModel.login;

import com.google.gson.annotations.SerializedName;

public class LoginDataModel {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("device_type")
    private String mDeviceType;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fcm_token")
    private String mFcmToken;
    @SerializedName("ftoken")
    private Object mFtoken;
    @SerializedName("id")
    private Long mId;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("name")
    private String mName;
    @SerializedName("notification")
    private Long mNotification;
    @SerializedName("picture")
    private Object mPicture;
    @SerializedName("token")
    private String mToken;
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

    public Object getFtoken() {
        return mFtoken;
    }

    public void setFtoken(Object ftoken) {
        mFtoken = ftoken;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Object getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        mModifiedAt = modifiedAt;
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

    public Object getPicture() {
        return mPicture;
    }

    public void setPicture(Object picture) {
        mPicture = picture;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getUsertype() {
        return mUsertype;
    }

    public void setUsertype(String usertype) {
        mUsertype = usertype;
    }

}
