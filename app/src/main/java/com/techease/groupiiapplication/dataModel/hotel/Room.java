
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("description")
    private Description mDescription;
    @SerializedName("type")
    private String mType;
    @SerializedName("typeEstimated")
    private TypeEstimated mTypeEstimated;

    public Description getDescription() {
        return mDescription;
    }

    public void setDescription(Description description) {
        mDescription = description;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public TypeEstimated getTypeEstimated() {
        return mTypeEstimated;
    }

    public void setTypeEstimated(TypeEstimated typeEstimated) {
        mTypeEstimated = typeEstimated;
    }

}
