
package com.techease.groupiiapplication.dataModel.hotel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HotelDataModel {

    @SerializedName("available")
    private Boolean mAvailable;
    @SerializedName("hotel")
    private Hotel mHotel;
    @SerializedName("offers")
    private List<Offer> mOffers;
    @SerializedName("self")
    private String mSelf;
    @SerializedName("type")
    private String mType;

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean available) {
        mAvailable = available;
    }

    public Hotel getHotel() {
        return mHotel;
    }

    public void setHotel(Hotel hotel) {
        mHotel = hotel;
    }

    public List<Offer> getOffers() {
        return mOffers;
    }

    public void setOffers(List<Offer> offers) {
        mOffers = offers;
    }

    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        mSelf = self;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
