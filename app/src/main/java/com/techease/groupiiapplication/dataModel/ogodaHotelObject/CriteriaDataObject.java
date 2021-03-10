package com.techease.groupiiapplication.dataModel.ogodaHotelObject;

public class CriteriaDataObject {


    private AreaDataObject area;
    private String checkInDate;
    private String checkOutDate;


    public CriteriaDataObject(AreaDataObject area, String checkInDate, String checkOutDate) {
        this.area = area;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public CriteriaDataObject() {
    }


    public AreaDataObject getAreaDataObject() {
        return area;
    }

    public void setAreaDataObject(AreaDataObject areaDataObject) {
        this.area = areaDataObject;
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
