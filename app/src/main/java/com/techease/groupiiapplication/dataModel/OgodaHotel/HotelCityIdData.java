package com.techease.groupiiapplication.dataModel.OgodaHotel;

public class HotelCityIdData {

    String cityName;
    int cityId;

    public HotelCityIdData(String cityName, int cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }


    public HotelCityIdData() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
