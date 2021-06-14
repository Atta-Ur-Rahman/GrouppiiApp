
package com.techease.groupiiapplication.dataModel.tripDetial.addPhotoToGallery;

import com.google.gson.annotations.SerializedName;

public class AddPhotoToGalleryResponse {

    @SerializedName("data")
    private AddPhotoToGalleryDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public AddPhotoToGalleryDataModel getData() {
        return mData;
    }

    public void setData(AddPhotoToGalleryDataModel data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
