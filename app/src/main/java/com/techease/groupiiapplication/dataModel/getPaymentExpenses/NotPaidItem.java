package com.techease.groupiiapplication.dataModel.getPaymentExpenses;

public class NotPaidItem{
	private int amount;
	private String name;
	private int percent;
	private String picture;

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPercent(int percent){
		this.percent = percent;
	}

	public int getPercent(){
		return percent;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return picture;
	}
}
