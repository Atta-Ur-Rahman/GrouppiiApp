
package com.techease.groupiiapplication.dataModel.updateProfilePicture;

import com.google.gson.annotations.SerializedName;

public class UpdateProfilePicResponse {

    @SerializedName("data")
    private UpdateProfilePicDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public UpdateProfilePicDataModel getData() {
        return mData;
    }

    public void setData(UpdateProfilePicDataModel data) {
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
