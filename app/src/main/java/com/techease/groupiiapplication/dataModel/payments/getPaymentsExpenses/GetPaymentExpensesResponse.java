
package com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses;
import com.google.gson.annotations.SerializedName;


public class GetPaymentExpensesResponse {

    @SerializedName("data")
    private GetPaymentExpensesDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Boolean mSuccess;

    public GetPaymentExpensesDataModel getData() {
        return mData;
    }

    public void setData(GetPaymentExpensesDataModel data) {
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
