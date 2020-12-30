
package com.techease.groupiiapplication.dataModel.addTrip;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class AddTripResponse {

    @SerializedName("data")
    private List<AddTripDataModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<AddTripDataModel> getData() {
        return mData;
    }

    public void setData(List<AddTripDataModel> data) {
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
