
package com.techease.groupiiapplication.dataModel.tripDetial.getGalleryPhoto;

import com.google.gson.annotations.SerializedName;

public class GalleryPhotoDataModel {

    @SerializedName("date")
    private String mDate;
    @SerializedName("file")
    private String mFile;
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

    public String getFile() {
        return mFile;
    }

    public void setFile(String file) {
        mFile = file;
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
