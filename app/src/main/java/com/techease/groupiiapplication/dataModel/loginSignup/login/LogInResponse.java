
package com.techease.groupiiapplication.dataModel.loginSignup.login;
import com.google.gson.annotations.SerializedName;


public class LogInResponse {

    @SerializedName("data")
    private LoginDataModel mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public LoginDataModel getData() {
        return mData;
    }

    public void setData(LoginDataModel data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
