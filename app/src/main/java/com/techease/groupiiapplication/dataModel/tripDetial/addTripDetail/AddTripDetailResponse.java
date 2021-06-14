
package com.techease.groupiiapplication.dataModel.tripDetial.addTripDetail;

import com.google.gson.annotations.SerializedName;

public class AddTripDetailResponse {

    @SerializedName("data")
    private AddTripDetailDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public AddTripDetailDataModel getData() {
        return mData;
    }

    public void setData(AddTripDetailDataModel data) {
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
