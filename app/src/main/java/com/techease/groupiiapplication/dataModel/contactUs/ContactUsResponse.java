
package com.techease.groupiiapplication.dataModel.contactUs;

import com.google.gson.annotations.SerializedName;


public class ContactUsResponse {

    @SerializedName("data")
    private ContactUsData mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public ContactUsData getData() {
        return mData;
    }

    public void setData(ContactUsData data) {
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
