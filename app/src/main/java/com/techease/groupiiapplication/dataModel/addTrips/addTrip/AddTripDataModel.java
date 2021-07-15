
package com.techease.groupiiapplication.dataModel.addTrips.addTrip;

import com.google.gson.annotations.SerializedName;

public class AddTripDataModel {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("picture")
    private Object mPicture;
    @SerializedName("shared_cost")
    private Long mSharedCost;
    @SerializedName("tripid")
    private Long mTripid;
    @SerializedName("userid")
    private Long mUserid;
    @SerializedName("name")
    private String mName;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;


    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Object getPicture() {
        return mPicture;
    }

    public void setPicture(Object picture) {
        mPicture = picture;
    }

    public Long getSharedCost() {
        return mSharedCost;
    }

    public void setSharedCost(Long sharedCost) {
        mSharedCost = sharedCost;
    }

    public Long getTripid() {
        return mTripid;
    }

    public void setTripid(Long tripid) {
        mTripid = tripid;
    }

    public Long getUserid() {
        return mUserid;
    }

    public void setUserid(Long userid) {
        mUserid = userid;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
