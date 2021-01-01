
package com.techease.groupiiapplication.dataModel.getAllTripDay;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AllTripDayResponse {

    @SerializedName("data")
    private List<AllTripDayDataModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<AllTripDayDataModel> getData() {
        return mData;
    }

    public void setData(List<AllTripDayDataModel> data) {
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
