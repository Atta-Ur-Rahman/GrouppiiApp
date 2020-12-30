
package com.techease.groupiiapplication.dataModel.hotel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HotelResponse {

    @SerializedName("data")
    private List<HotelDataModel> mData;
    @SerializedName("meta")
    private Meta mMeta;

    public List<HotelDataModel> getData() {
        return mData;
    }

    public void setData(List<HotelDataModel> data) {
        mData = data;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

}
