
package com.techease.groupiiapplication.dataModel.hotel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Hotel {

    @SerializedName("address")
    private Address mAddress;
    @SerializedName("amenities")
    private List<String> mAmenities;
    @SerializedName("chainCode")
    private String mChainCode;
    @SerializedName("cityCode")
    private String mCityCode;
    @SerializedName("contact")
    private Contact mContact;
    @SerializedName("description")
    private Description mDescription;
    @SerializedName("dupeId")
    private String mDupeId;
    @SerializedName("hotelDistance")
    private HotelDistance mHotelDistance;
    @SerializedName("hotelId")
    private String mHotelId;
    @SerializedName("latitude")
    private Double mLatitude;
    @SerializedName("longitude")
    private Double mLongitude;
    @SerializedName("media")
    private List<Medium> mMedia;
    @SerializedName("name")
    private String mName;
    @SerializedName("rating")
    private String mRating;
    @SerializedName("type")
    private String mType;

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public List<String> getAmenities() {
        return mAmenities;
    }

    public void setAmenities(List<String> amenities) {
        mAmenities = amenities;
    }

    public String getChainCode() {
        return mChainCode;
    }

    public void setChainCode(String chainCode) {
        mChainCode = chainCode;
    }

    public String getCityCode() {
        return mCityCode;
    }

    public void setCityCode(String cityCode) {
        mCityCode = cityCode;
    }

    public Contact getContact() {
        return mContact;
    }

    public void setContact(Contact contact) {
        mContact = contact;
    }

    public Description getDescription() {
        return mDescription;
    }

    public void setDescription(Description description) {
        mDescription = description;
    }

    public String getDupeId() {
        return mDupeId;
    }

    public void setDupeId(String dupeId) {
        mDupeId = dupeId;
    }

    public HotelDistance getHotelDistance() {
        return mHotelDistance;
    }

    public void setHotelDistance(HotelDistance hotelDistance) {
        mHotelDistance = hotelDistance;
    }

    public String getHotelId() {
        return mHotelId;
    }

    public void setHotelId(String hotelId) {
        mHotelId = hotelId;
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

    public List<Medium> getMedia() {
        return mMedia;
    }

    public void setMedia(List<Medium> media) {
        mMedia = media;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
