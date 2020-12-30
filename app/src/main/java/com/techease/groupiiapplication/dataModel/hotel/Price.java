
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("currency")
    private String mCurrency;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("variations")
    private Variations mVariations;

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public Variations getVariations() {
        return mVariations;
    }

    public void setVariations(Variations variations) {
        mVariations = variations;
    }

}
