package com.techease.groupiiapplication.dataModel.ogodaHotelObject;

public class AreaDataObject {

    private int id;
    private int cityId;


    public AreaDataObject(int id, int cityId) {
        this.id = id;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public AreaDataObject() {
    }
}
