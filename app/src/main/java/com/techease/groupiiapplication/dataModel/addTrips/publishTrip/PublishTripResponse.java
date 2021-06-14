
package com.techease.groupiiapplication.dataModel.addTrips.publishTrip;

import com.google.gson.annotations.SerializedName;

public class PublishTripResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

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
