
package com.techease.groupiiapplication.dataModel.forgot;
import com.google.gson.annotations.SerializedName;

public class ForgotResponse {

    @SerializedName("data")
    private Long mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public Long getData() {
        return mData;
    }

    public void setData(Long data) {
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
