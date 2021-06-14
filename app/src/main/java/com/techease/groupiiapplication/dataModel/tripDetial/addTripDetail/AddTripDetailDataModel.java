
package com.techease.groupiiapplication.dataModel.tripDetial.addTripDetail;

import com.google.gson.annotations.SerializedName;

public class AddTripDetailDataModel {

    @SerializedName("coverimage")
    private String mCoverimage;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("enddate")
    private String mEnddate;
    @SerializedName("id")
    private String mId;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("paydate")
    private String mPaydate;
    @SerializedName("startdate")
    private String mStartdate;
    @SerializedName("title")
    private String mTitle;

    public String getCoverimage() {
        return mCoverimage;
    }

    public void setCoverimage(String coverimage) {
        mCoverimage = coverimage;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEnddate() {
        return mEnddate;
    }

    public void setEnddate(String enddate) {
        mEnddate = enddate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getPaydate() {
        return mPaydate;
    }

    public void setPaydate(String paydate) {
        mPaydate = paydate;
    }

    public String getStartdate() {
        return mStartdate;
    }

    public void setStartdate(String startdate) {
        mStartdate = startdate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
