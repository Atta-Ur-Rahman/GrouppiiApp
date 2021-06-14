
package com.techease.groupiiapplication.dataModel.tripDetial.getUserTrip;
import com.google.gson.annotations.SerializedName;


public class GetUserTripData {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("shared_cost")
    private Long mSharedCost;
    @SerializedName("tripid")
    private Long mTripid;
    @SerializedName("userid")
    private Long mUserid;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

}
