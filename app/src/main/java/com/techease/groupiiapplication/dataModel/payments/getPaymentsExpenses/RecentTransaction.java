
package com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses;
import com.google.gson.annotations.SerializedName;

public class RecentTransaction {

    @SerializedName("amount")
    private Long mAmount;
    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private Long mId;
    @SerializedName("is_personal")
    private Long mIsPersonal;
    @SerializedName("name")
    private String mName;
    @SerializedName("paid")
    private Long mPaid;
    @SerializedName("paid_by")
    private Long mPaidBy;
    @SerializedName("short_desc")
    private String mShortDesc;
    @SerializedName("source")
    private String mSource;
    @SerializedName("tripid")
    private Long mTripid;
    @SerializedName("type_image")
    private String mTypeImage;
    @SerializedName("userid")
    private Long mUserid;

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
        mAmount = amount;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getIsPersonal() {
        return mIsPersonal;
    }

    public void setIsPersonal(Long isPersonal) {
        mIsPersonal = isPersonal;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getPaid() {
        return mPaid;
    }

    public void setPaid(Long paid) {
        mPaid = paid;
    }

    public Long getPaidBy() {
        return mPaidBy;
    }

    public void setPaidBy(Long paidBy) {
        mPaidBy = paidBy;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public void setShortDesc(String shortDesc) {
        mShortDesc = shortDesc;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public Long getTripid() {
        return mTripid;
    }

    public void setTripid(Long tripid) {
        mTripid = tripid;
    }

    public String getTypeImage() {
        return mTypeImage;
    }

    public void setTypeImage(String typeImage) {
        mTypeImage = typeImage;
    }

    public Long getUserid() {
        return mUserid;
    }

    public void setUserid(Long userid) {
        mUserid = userid;
    }

}
