package com.techease.groupiiapplication.dataModel.reserveModel;

import com.google.gson.annotations.SerializedName;

public class AddReserveData {

    @SerializedName("image")
    private String image;

    @SerializedName("is_done")
    private String isDone;

    @SerializedName("address")
    private String address;

    @SerializedName("confirmation_code")
    private String confirmationCode;

    @SerializedName("todate")
    private String todate;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("tripid")
    private String tripid;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("fromdate")
    private String fromdate;

    public String getImage() {
        return image;
    }

    public String getIsDone() {
        return isDone;
    }

    public String getAddress() {
        return address;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public String getTodate() {
        return todate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTripid() {
        return tripid;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFromdate() {
        return fromdate;
    }
}