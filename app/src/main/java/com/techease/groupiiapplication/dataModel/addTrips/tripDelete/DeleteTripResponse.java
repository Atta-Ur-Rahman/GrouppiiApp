
package com.techease.groupiiapplication.dataModel.addTrips.tripDelete;

import com.google.gson.annotations.SerializedName;
public class DeleteTripResponse {

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
