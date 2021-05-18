package com.techease.groupiiapplication.dataModel.ogodaHotelObject;

public class CriteriaDataObject {


    private AreaDataObject area;
    private String checkInDate;
    private String checkOutDate;
    private int cityId;



    public CriteriaDataObject(int cityId, String checkInDate, String checkOutDate) {

        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.cityId = cityId;
    }

    public CriteriaDataObject() {
    }



    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
