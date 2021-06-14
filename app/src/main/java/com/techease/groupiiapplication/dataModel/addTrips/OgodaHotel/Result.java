
package com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel;
import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("crossedOutRate")
    private Double mCrossedOutRate;
    @SerializedName("currency")
    private String mCurrency;
    @SerializedName("dailyRate")
    private Double mDailyRate;
    @SerializedName("discountPercentage")
    private Double mDiscountPercentage;
    @SerializedName("freeWifi")
    private Boolean mFreeWifi;
    @SerializedName("hotelId")
    private Long mHotelId;
    @SerializedName("hotelName")
    private String mHotelName;
    @SerializedName("imageURL")
    private String mImageURL;
    @SerializedName("includeBreakfast")
    private Boolean mIncludeBreakfast;
    @SerializedName("landingURL")
    private String mLandingURL;
    @SerializedName("latitude")
    private Double mLatitude;
    @SerializedName("longitude")
    private Double mLongitude;
    @SerializedName("reviewCount")
    private Long mReviewCount;
    @SerializedName("reviewScore")
    private Double mReviewScore;
    @SerializedName("starRating")
    private Double mStarRating;

    public Double getCrossedOutRate() {
        return mCrossedOutRate;
    }

    public void setCrossedOutRate(Double crossedOutRate) {
        mCrossedOutRate = crossedOutRate;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public Double getDailyRate() {
        return mDailyRate;
    }

    public void setDailyRate(Double dailyRate) {
        mDailyRate = dailyRate;
    }

    public Double getDiscountPercentage() {
        return mDiscountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        mDiscountPercentage = discountPercentage;
    }

    public Boolean getFreeWifi() {
        return mFreeWifi;
    }

    public void setFreeWifi(Boolean freeWifi) {
        mFreeWifi = freeWifi;
    }

    public Long getHotelId() {
        return mHotelId;
    }

    public void setHotelId(Long hotelId) {
        mHotelId = hotelId;
    }

    public String getHotelName() {
        return mHotelName;
    }

    public void setHotelName(String hotelName) {
        mHotelName = hotelName;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public Boolean getIncludeBreakfast() {
        return mIncludeBreakfast;
    }

    public void setIncludeBreakfast(Boolean includeBreakfast) {
        mIncludeBreakfast = includeBreakfast;
    }

    public String getLandingURL() {
        return mLandingURL;
    }

    public void setLandingURL(String landingURL) {
        mLandingURL = landingURL;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public Long getReviewCount() {
        return mReviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        mReviewCount = reviewCount;
    }

    public Double getReviewScore() {
        return mReviewScore;
    }

    public void setReviewScore(Double reviewScore) {
        mReviewScore = reviewScore;
    }

    public Double getStarRating() {
        return mStarRating;
    }

    public void setStarRating(Double starRating) {
        mStarRating = starRating;
    }

}
