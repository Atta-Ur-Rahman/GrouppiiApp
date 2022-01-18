package com.techease.groupiiapplication.dataModel.getReserveModel;

import com.google.gson.annotations.SerializedName;

public class GetReserveDataModel {

    @SerializedName("image")
    private String image;

    @SerializedName("is_done")
    private int isDone;

    @SerializedName("address")
    private String address;

    @SerializedName("todate")
    private String todate;

    @SerializedName("confirmation_code")
    private String confirmation;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("tripid")
    private int tripid;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("modified_at")
    private Object modifiedAt;

    @SerializedName("fromdate")
    private String fromdate;

    public String getImage() {
        return image;
    }

    public int getIsDone() {
        return isDone;
    }

    public String getAddress() {
        return address;
    }

    public String getTodate() {
        return todate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getTripid() {
        return tripid;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Object getModifiedAt() {
        return modifiedAt;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getConfirmation() {
        return confirmation;
    }
}