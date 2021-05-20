package com.techease.groupiiapplication.dataModel.getPaymentExpenses;

import java.util.List;

public class GetPaymentExpensesData {
	private int paidPercent;
	private List<GroupExpendituresItem> groupExpenditures;
	private List<Object> partialPaid;
	private List<Object> personalExpenditures;
	private List<Object> fullPaid;
	private String tripdate;
	private int notPaidUsers;
	private int partialPaidUsers;
	private int totalpayment;
	private int fullyPaidUsers;
	private int totalUsers;
	private List<NotPaidItem> notPaid;

	public void setPaidPercent(int paidPercent){
		this.paidPercent = paidPercent;
	}

	public int getPaidPercent(){
		return paidPercent;
	}

	public void setGroupExpenditures(List<GroupExpendituresItem> groupExpenditures){
		this.groupExpenditures = groupExpenditures;
	}

	public List<GroupExpendituresItem> getGroupExpenditures(){
		return groupExpenditures;
	}

	public void setPartialPaid(List<Object> partialPaid){
		this.partialPaid = partialPaid;
	}

	public List<Object> getPartialPaid(){
		return partialPaid;
	}

	public void setPersonalExpenditures(List<Object> personalExpenditures){
		this.personalExpenditures = personalExpenditures;
	}

	public List<Object> getPersonalExpenditures(){
		return personalExpenditures;
	}

	public void setFullPaid(List<Object> fullPaid){
		this.fullPaid = fullPaid;
	}

	public List<Object> getFullPaid(){
		return fullPaid;
	}

	public void setTripdate(String tripdate){
		this.tripdate = tripdate;
	}

	public String getTripdate(){
		return tripdate;
	}

	public void setNotPaidUsers(int notPaidUsers){
		this.notPaidUsers = notPaidUsers;
	}

	public int getNotPaidUsers(){
		return notPaidUsers;
	}

	public void setPartialPaidUsers(int partialPaidUsers){
		this.partialPaidUsers = partialPaidUsers;
	}

	public int getPartialPaidUsers(){
		return partialPaidUsers;
	}

	public void setTotalpayment(int totalpayment){
		this.totalpayment = totalpayment;
	}

	public int getTotalpayment(){
		return totalpayment;
	}

	public void setFullyPaidUsers(int fullyPaidUsers){
		this.fullyPaidUsers = fullyPaidUsers;
	}

	public int getFullyPaidUsers(){
		return fullyPaidUsers;
	}

	public void setTotalUsers(int totalUsers){
		this.totalUsers = totalUsers;
	}

	public int getTotalUsers(){
		return totalUsers;
	}

	public void setNotPaid(List<NotPaidItem> notPaid){
		this.notPaid = notPaid;
	}

	public List<NotPaidItem> getNotPaid(){
		return notPaid;
	}
}