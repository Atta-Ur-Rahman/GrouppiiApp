
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Change {

    @SerializedName("endDate")
    private String mEndDate;
    @SerializedName("startDate")
    private String mStartDate;
    @SerializedName("total")
    private String mTotal;

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

}
