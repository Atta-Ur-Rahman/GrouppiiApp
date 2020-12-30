
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("checkInDate")
    private String mCheckInDate;
    @SerializedName("checkOutDate")
    private String mCheckOutDate;
    @SerializedName("commission")
    private Commission mCommission;
    @SerializedName("guests")
    private Guests mGuests;
    @SerializedName("id")
    private String mId;
    @SerializedName("policies")
    private Policies mPolicies;
    @SerializedName("price")
    private Price mPrice;
    @SerializedName("rateCode")
    private String mRateCode;
    @SerializedName("rateFamilyEstimated")
    private RateFamilyEstimated mRateFamilyEstimated;
    @SerializedName("room")
    private Room mRoom;

    public String getCheckInDate() {
        return mCheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        mCheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return mCheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        mCheckOutDate = checkOutDate;
    }

    public Commission getCommission() {
        return mCommission;
    }

    public void setCommission(Commission commission) {
        mCommission = commission;
    }

    public Guests getGuests() {
        return mGuests;
    }

    public void setGuests(Guests guests) {
        mGuests = guests;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Policies getPolicies() {
        return mPolicies;
    }

    public void setPolicies(Policies policies) {
        mPolicies = policies;
    }

    public Price getPrice() {
        return mPrice;
    }

    public void setPrice(Price price) {
        mPrice = price;
    }

    public String getRateCode() {
        return mRateCode;
    }

    public void setRateCode(String rateCode) {
        mRateCode = rateCode;
    }

    public RateFamilyEstimated getRateFamilyEstimated() {
        return mRateFamilyEstimated;
    }

    public void setRateFamilyEstimated(RateFamilyEstimated rateFamilyEstimated) {
        mRateFamilyEstimated = rateFamilyEstimated;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }

}
