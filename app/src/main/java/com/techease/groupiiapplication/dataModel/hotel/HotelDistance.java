
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class HotelDistance {

    @SerializedName("distance")
    private Double mDistance;
    @SerializedName("distanceUnit")
    private String mDistanceUnit;

    public Double getDistance() {
        return mDistance;
    }

    public void setDistance(Double distance) {
        mDistance = distance;
    }

    public String getDistanceUnit() {
        return mDistanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        mDistanceUnit = distanceUnit;
    }

}
