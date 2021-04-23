
package com.techease.groupiiapplication.dataModel.tripDetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TripDetailDataModel {

    @SerializedName("active")
    private List<Active> mActive;
    @SerializedName("past")
    private List<Past> mPast;
    @SerializedName("upcoming")
    private List<Upcoming> mUpcoming;

    @SerializedName("unpublish")
    private List<Unpublish> mUnpublish;

    public List<Active> getActive() {
        return mActive;
    }

    public void setActive(List<Active> active) {
        mActive = active;
    }

    public List<Past> getPast() {
        return mPast;
    }

    public void setPast(List<Past> past) {
        mPast = past;
    }

    public List<Upcoming> getUpcoming() {
        return mUpcoming;
    }

    public void setUpcoming(List<Upcoming> upcoming) {
        mUpcoming = upcoming;
    }

    public List<Unpublish> getUnpublish() {
        return mUnpublish;
    }
    public void setmUnpublish(List<Unpublish> unpublish) {
        mUnpublish = unpublish;
    }

}
