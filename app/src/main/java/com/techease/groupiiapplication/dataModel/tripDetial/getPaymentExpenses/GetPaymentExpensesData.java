package com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class GetPaymentExpensesData {

	@SerializedName("tripdate")
	@Expose
	private String tripdate;
	@SerializedName("paid_percent")
	@Expose
	private Integer paidPercent;
	@SerializedName("totalpayment")
	@Expose
	private Integer totalpayment;
	@SerializedName("group_expenditures")
	@Expose
	private List<GroupExpendituresItem> groupExpenditures = null;
	@SerializedName("personal_expenditures")
	@Expose
	private List<PersonalExpendituresItem> personalExpenditures = null;
	@SerializedName("total_users")
	@Expose
	private Integer totalUsers;
	@SerializedName("fully_paid_users")
	@Expose
	private Integer fullyPaidUsers;
	@SerializedName("partial_paid_users")
	@Expose
	private Integer partialPaidUsers;
	@SerializedName("not_paid_users")
	@Expose
	private Integer notPaidUsers;
	@SerializedName("full_paid")
	@Expose
	private List<Object> fullPaid = null;
	@SerializedName("partial_paid")
	@Expose
	private List<Object> partialPaid = null;
	@SerializedName("not_paid")
	@Expose
	private List<NotPaidItem> notPaid = null;

	public String getTripdate() {
		return tripdate;
	}

	public void setTripdate(String tripdate) {
		this.tripdate = tripdate;
	}

	public Integer getPaidPercent() {
		return paidPercent;
	}

	public void setPaidPercent(Integer paidPercent) {
		this.paidPercent = paidPercent;
	}

	public Integer getTotalpayment() {
		return totalpayment;
	}

	public void setTotalpayment(Integer totalpayment) {
		this.totalpayment = totalpayment;
	}

	public List<GroupExpendituresItem> getGroupExpenditures() {
		return groupExpenditures;
	}

	public void setGroupExpenditures(List<GroupExpendituresItem> groupExpenditures) {
		this.groupExpenditures = groupExpenditures;
	}

	public List<PersonalExpendituresItem> getPersonalExpenditures() {
		return personalExpenditures;
	}

	public void setPersonalExpenditures(List<PersonalExpendituresItem> personalExpenditures) {
		this.personalExpenditures = personalExpenditures;
	}

	public Integer getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Integer getFullyPaidUsers() {
		return fullyPaidUsers;
	}

	public void setFullyPaidUsers(Integer fullyPaidUsers) {
		this.fullyPaidUsers = fullyPaidUsers;
	}

	public Integer getPartialPaidUsers() {
		return partialPaidUsers;
	}

	public void setPartialPaidUsers(Integer partialPaidUsers) {
		this.partialPaidUsers = partialPaidUsers;
	}

	public Integer getNotPaidUsers() {
		return notPaidUsers;
	}

	public void setNotPaidUsers(Integer notPaidUsers) {
		this.notPaidUsers = notPaidUsers;
	}

	public List<Object> getFullPaid() {
		return fullPaid;
	}

	public void setFullPaid(List<Object> fullPaid) {
		this.fullPaid = fullPaid;
	}

	public List<Object> getPartialPaid() {
		return partialPaid;
	}

	public void setPartialPaid(List<Object> partialPaid) {
		this.partialPaid = partialPaid;
	}

	public List<NotPaidItem> getNotPaid() {
		return notPaid;
	}

	public void setNotPaid(List<NotPaidItem> notPaid) {
		this.notPaid = notPaid;
	}

}
