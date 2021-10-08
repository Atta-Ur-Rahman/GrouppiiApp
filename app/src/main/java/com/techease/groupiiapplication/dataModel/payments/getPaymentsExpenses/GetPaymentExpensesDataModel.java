
package com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class GetPaymentExpensesDataModel {

    @SerializedName("each_user_topay")
    private Double mEachUserTopay;
    @SerializedName("full_paid")
    private List<FullPaid> mFullPaid;
    @SerializedName("fully_paid_users")
    private Long mFullyPaidUsers;
    @SerializedName("group_expenditures")
    private List<GroupExpenditure> mGroupExpenditures;
    @SerializedName("not_paid")
    private List<NotPaid> mNotPaid;
    @SerializedName("not_paid_users")
    private Long mNotPaidUsers;
    @SerializedName("paid_percent")
    private Double mPaidPercent;
    @SerializedName("partial_paid")
    private List<PartialPaid> mPartialPaid;
    @SerializedName("partial_paid_users")
    private Long mPartialPaidUsers;
    @SerializedName("personal_expenditures")
    private List<PersonalExpenditure> mPersonalExpenditures;
    @SerializedName("recent_transaction")
    private List<RecentTransaction> mRecentTransaction;
    @SerializedName("recievedpayment")
    private Double mRecievedpayment;
    @SerializedName("shares_no_cost")
    private List<SharesNoCost> mSharesNoCost;
    @SerializedName("total_users")
    private Long mTotalUsers;
    @SerializedName("totalpayment")
    private Double mTotalpayment;
    @SerializedName("tripdate")
    private String mTripdate;

    public Double getEachUserTopay() {
        return mEachUserTopay;
    }

    public void setEachUserTopay(Double eachUserTopay) {
        mEachUserTopay = eachUserTopay;
    }

    public List<FullPaid> getFullPaid() {
        return mFullPaid;
    }

    public void setFullPaid(List<FullPaid> fullPaid) {
        mFullPaid = fullPaid;
    }

    public Long getFullyPaidUsers() {
        return mFullyPaidUsers;
    }

    public void setFullyPaidUsers(Long fullyPaidUsers) {
        mFullyPaidUsers = fullyPaidUsers;
    }

    public List<GroupExpenditure> getGroupExpenditures() {
        return mGroupExpenditures;
    }

    public void setGroupExpenditures(List<GroupExpenditure> groupExpenditures) {
        mGroupExpenditures = groupExpenditures;
    }

    public List<NotPaid> getNotPaid() {
        return mNotPaid;
    }

    public void setNotPaid(List<NotPaid> notPaid) {
        mNotPaid = notPaid;
    }

    public Long getNotPaidUsers() {
        return mNotPaidUsers;
    }

    public void setNotPaidUsers(Long notPaidUsers) {
        mNotPaidUsers = notPaidUsers;
    }

    public Double   getPaidPercent() {
        return mPaidPercent;
    }

    public void setPaidPercent(Double paidPercent) {
        mPaidPercent = paidPercent;
    }

    public List<PartialPaid> getPartialPaid() {
        return mPartialPaid;
    }

    public void setPartialPaid(List<PartialPaid> partialPaid) {
        mPartialPaid = partialPaid;
    }

    public Long getPartialPaidUsers() {
        return mPartialPaidUsers;
    }

    public void setPartialPaidUsers(Long partialPaidUsers) {
        mPartialPaidUsers = partialPaidUsers;
    }

    public List<PersonalExpenditure> getPersonalExpenditures() {
        return mPersonalExpenditures;
    }

    public void setPersonalExpenditures(List<PersonalExpenditure> personalExpenditures) {
        mPersonalExpenditures = personalExpenditures;
    }

    public List<RecentTransaction> getRecentTransaction() {
        return mRecentTransaction;
    }

    public void setRecentTransaction(List<RecentTransaction> recentTransaction) {
        mRecentTransaction = recentTransaction;
    }

    public Double getRecievedpayment() {
        return mRecievedpayment;
    }

    public void setRecievedpayment(Double recievedpayment) {
        mRecievedpayment = recievedpayment;
    }

    public List<SharesNoCost> getSharesNoCost() {
        return mSharesNoCost;
    }

    public void setSharesNoCost(List<SharesNoCost> sharesNoCost) {
        mSharesNoCost = sharesNoCost;
    }

    public Long getTotalUsers() {
        return mTotalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        mTotalUsers = totalUsers;
    }

    public Double getTotalpayment() {
        return mTotalpayment;
    }

    public void setTotalpayment(Double totalpayment) {
        mTotalpayment = totalpayment;
    }

    public String getTripdate() {
        return mTripdate;
    }

    public void setTripdate(String tripdate) {
        mTripdate = tripdate;
    }

}
