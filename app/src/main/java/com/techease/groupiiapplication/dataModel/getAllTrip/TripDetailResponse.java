
package com.techease.groupiiapplication.dataModel.getAllTrip;
import com.google.gson.annotations.SerializedName;

public class TripDetailResponse {

    @SerializedName("data")
    private TripDetailDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public TripDetailDataModel getData() {
        return mData;
    }

    public void setData(TripDetailDataModel data) {
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
