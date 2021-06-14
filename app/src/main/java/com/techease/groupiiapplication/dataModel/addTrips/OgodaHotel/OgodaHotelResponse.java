
package com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OgodaHotelResponse {

    @SerializedName("results")
    private List<Result> mResults;

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

}
