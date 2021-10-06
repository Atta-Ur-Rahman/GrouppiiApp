
package com.techease.groupiiapplication.dataModel.updateUserProfile;

import com.google.gson.annotations.SerializedName;

public class UpdateUserProfileResponse {

    @SerializedName("data")
    private UpdateUserProfileData mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public UpdateUserProfileData getData() {
        return mData;
    }

    public void setData(UpdateUserProfileData data) {
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
