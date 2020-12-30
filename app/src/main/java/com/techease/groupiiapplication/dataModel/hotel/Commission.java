
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Commission {

    @SerializedName("percentage")
    private String mPercentage;

    public String getPercentage() {
        return mPercentage;
    }

    public void setPercentage(String percentage) {
        mPercentage = percentage;
    }

}
