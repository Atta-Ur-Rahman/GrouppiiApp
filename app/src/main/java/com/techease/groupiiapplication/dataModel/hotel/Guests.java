
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Guests {

    @SerializedName("adults")
    private Long mAdults;

    public Long getAdults() {
        return mAdults;
    }

    public void setAdults(Long adults) {
        mAdults = adults;
    }

}
