
package com.techease.groupiiapplication.dataModel.loginSignup.signUp;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("data")
    private SignUpDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public SignUpDataModel getData() {
        return mData;
    }

    public void setData(SignUpDataModel data) {
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
