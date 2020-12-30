
package com.techease.groupiiapplication.dataModel.hotel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("cityName")
    private String mCityName;
    @SerializedName("countryCode")
    private String mCountryCode;
    @SerializedName("lines")
    private List<String> mLines;
    @SerializedName("postalCode")
    private String mPostalCode;

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public List<String> getLines() {
        return mLines;
    }

    public void setLines(List<String> lines) {
        mLines = lines;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

}
