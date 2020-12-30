
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("next")
    private String mNext;

    public String getNext() {
        return mNext;
    }

    public void setNext(String next) {
        mNext = next;
    }

}
