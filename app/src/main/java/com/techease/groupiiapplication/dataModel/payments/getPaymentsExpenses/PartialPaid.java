
package com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses;

import com.google.gson.annotations.SerializedName;

public class PartialPaid {

    @SerializedName("amount")
    private Long mAmount;
    @SerializedName("name")
    private String mName;
    @SerializedName("percent")
    private Float mPercent;
    @SerializedName("picture")
    private Object mPicture;

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
        mAmount = amount;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Float getPercent() {
        return mPercent;
    }

    public void setPercent(Float percent) {
        mPercent = percent;
    }

    public Object getPicture() {
        return mPicture;
    }

    public void setPicture(Object picture) {
        mPicture = picture;
    }

}
