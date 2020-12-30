
package com.techease.groupiiapplication.dataModel.tripDetail;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("is_accepted")
    private Long mIsAccepted;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("picture")
    private Object mPicture;
    @SerializedName("shared_cost")
    private Long mSharedCost;
    @SerializedName("tripid")
    private Long mTripid;
    @SerializedName("userid")
    private Long mUserid;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getIsAccepted() {
        return mIsAccepted;
    }

    public void setIsAccepted(Long isAccepted) {
        mIsAccepted = isAccepted;
    }

    public Object getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        mModifiedAt = modifiedAt;
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

}
