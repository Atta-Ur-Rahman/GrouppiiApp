
package com.techease.groupiiapplication.dataModel.hotel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Variations {

    @SerializedName("average")
    private Average mAverage;
    @SerializedName("changes")
    private List<Change> mChanges;

    public Average getAverage() {
        return mAverage;
    }

    public void setAverage(Average average) {
        mAverage = average;
    }

    public List<Change> getChanges() {
        return mChanges;
    }

    public void setChanges(List<Change> changes) {
        mChanges = changes;
    }

}
