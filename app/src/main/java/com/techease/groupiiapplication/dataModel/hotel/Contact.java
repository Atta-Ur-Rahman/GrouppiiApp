
package com.techease.groupiiapplication.dataModel.hotel;

import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("fax")
    private String mFax;
    @SerializedName("phone")
    private String mPhone;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFax() {
        return mFax;
    }

    public void setFax(String fax) {
        mFax = fax;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

}
