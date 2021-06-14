
package com.techease.groupiiapplication.dataModel.tripDetial.getGalleryPhoto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetGalleryPhotoResponse {

    @SerializedName("data")
    private List<GalleryPhotoDataModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<GalleryPhotoDataModel> getData() {
        return mData;
    }

    public void setData(List<GalleryPhotoDataModel> data) {
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
