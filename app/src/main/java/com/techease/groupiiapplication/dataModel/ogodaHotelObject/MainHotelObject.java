package com.techease.groupiiapplication.dataModel.ogodaHotelObject;

public class MainHotelObject {


    private CriteriaDataObject criteria;

    public MainHotelObject(CriteriaDataObject criteriaDataObject) {
        this.criteria = criteriaDataObject;
    }

    public CriteriaDataObject getCriteriaDataObject() {
        return criteria;
    }

    public void setCriteriaDataObject( CriteriaDataObject criteriaDataObject) {
        this.criteria = criteriaDataObject;
    }

    public MainHotelObject() {
    }
}
