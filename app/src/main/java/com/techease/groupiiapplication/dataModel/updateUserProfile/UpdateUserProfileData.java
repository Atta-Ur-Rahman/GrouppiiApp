
package com.techease.groupiiapplication.dataModel.updateUserProfile;

import com.google.gson.annotations.SerializedName;


public class UpdateUserProfileData {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
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
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("longitude")
    private String mLongitude;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("name")
    private String mName;
    @SerializedName("notification")
    private Object mNotification;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("usertype")
    private String mUsertype;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
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

    public Object getNotification() {
        return mNotification;
    }

    public void setNotification(Object notification) {
        mNotification = notification;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getUsertype() {
        return mUsertype;
    }

    public void setUsertype(String usertype) {
        mUsertype = usertype;
    }

}
