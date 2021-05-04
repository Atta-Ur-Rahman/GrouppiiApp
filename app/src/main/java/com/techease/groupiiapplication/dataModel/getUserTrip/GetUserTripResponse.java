
package com.techease.groupiiapplication.dataModel.getUserTrip;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserTripResponse {

    @SerializedName("data")
    private List<GetUserTripData> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<GetUserTripData> getData() {
        return mData;
    }

    public void setData(List<GetUserTripData> data) {
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
