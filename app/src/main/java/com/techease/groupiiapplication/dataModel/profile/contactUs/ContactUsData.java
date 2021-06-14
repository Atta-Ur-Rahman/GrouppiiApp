
package com.techease.groupiiapplication.dataModel.profile.contactUs;
import com.google.gson.annotations.SerializedName;

public class ContactUsData {

    @SerializedName("contact")
    private String mContact;
    @SerializedName("userid")
    private String mUserid;

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getUserid() {
        return mUserid;
    }

    public void setUserid(String userid) {
        mUserid = userid;
    }

}
