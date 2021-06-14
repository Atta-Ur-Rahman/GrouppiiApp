
package com.techease.groupiiapplication.dataModel.tripDetial.addTripDay;

import com.google.gson.annotations.SerializedName;

public class AddTripDayResponse {

    @SerializedName("data")
    private AddTripDayDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public AddTripDayDataModel getData() {
        return mData;
    }

    public void setData(AddTripDayDataModel data) {
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
