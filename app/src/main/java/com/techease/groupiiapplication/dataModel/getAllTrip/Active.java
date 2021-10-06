
package com.techease.groupiiapplication.dataModel.getAllTrip;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Active {

    @SerializedName("coverimage")
    private String mCoverimage;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("fromdate")
    private String mFromdate;
    @SerializedName("id")
    private Long mId;
    @SerializedName("is_published")
    private String mIsPublished;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("pay_date")
    private String mPayDate;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("todate")
    private String mTodate;
    @SerializedName("userid")
    private Long mUserid;
    @SerializedName("tripid")
    private Long mTripid;
    @SerializedName("users")
    private List<User> mUsers;


    @SerializedName("is_createdby")
    private boolean isCreatedby;

    public String getCoverimage() {
        return mCoverimage;
    }

    public void setCoverimage(String coverimage) {
        mCoverimage = coverimage;
    }

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

    public String getFromdate() {
        return mFromdate;
    }

    public void setFromdate(String fromdate) {
        mFromdate = fromdate;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getIsPublished() {
        return mIsPublished;
    }

    public void setIsPublished(String isPublished) {
        mIsPublished = isPublished;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public Object getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        mModifiedAt = modifiedAt;
    }

    public String getPayDate() {
        return mPayDate;
    }

    public void setPayDate(String payDate) {
        mPayDate = payDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTodate() {
        return mTodate;
    }

    public void setTodate(String todate) {
        mTodate = todate;
    }

    public Long getUserid() {
        return mUserid;
    }

    public void setUserid(Long userid) {
        mUserid = userid;
    }

    public void setTripid(Long tripid) {
        mTripid = tripid;
    }

    public Long getTripid() {
        return mTripid;
    }

    public void setIsCreatedby(boolean isCreatedby) {
        this.isCreatedby = isCreatedby;
    }

    public boolean isIsCreatedby() {
        return isCreatedby;
    }


    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

}
