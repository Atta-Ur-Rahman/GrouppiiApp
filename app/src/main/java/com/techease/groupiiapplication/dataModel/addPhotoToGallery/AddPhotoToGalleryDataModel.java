
package com.techease.groupiiapplication.dataModel.addPhotoToGallery;

import com.google.gson.annotations.SerializedName;
public class AddPhotoToGalleryDataModel {

    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private Long mId;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("time")
    private String mTime;
    @SerializedName("title")
    private String mTitle;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
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

}
