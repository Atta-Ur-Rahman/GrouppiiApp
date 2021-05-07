
package com.techease.groupiiapplication.dataModel.addTripDay;

import com.google.gson.annotations.SerializedName;
public class AddTripDayDataModel {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("date")
    private String mDate;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private Long mId;
    @SerializedName("time")
    private String mTime;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("tripid")
    private String mTripid;
    @SerializedName("userid")
    private String mUserid;
    @SerializedName("type")
    private String mType;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTripid() {
        return mTripid;
    }

    public void setTripid(String tripid) {
        mTripid = tripid;
    }

    public String getUserid() {
        return mUserid;
    }

    public void setUserid(String userid) {
        mUserid = userid;
    }

    public String getType() {
        return mType;
    }
    public void setType(String type) {
        mType = type;
    }



}
