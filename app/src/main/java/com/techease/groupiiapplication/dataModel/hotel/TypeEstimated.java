
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class TypeEstimated {

    @SerializedName("bedType")
    private String mBedType;
    @SerializedName("beds")
    private Long mBeds;
    @SerializedName("category")
    private String mCategory;

    public String getBedType() {
        return mBedType;
    }

    public void setBedType(String bedType) {
        mBedType = bedType;
    }

    public Long getBeds() {
        return mBeds;
    }

    public void setBeds(Long beds) {
        mBeds = beds;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

}
