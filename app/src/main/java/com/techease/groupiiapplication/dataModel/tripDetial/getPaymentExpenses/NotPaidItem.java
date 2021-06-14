package com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotPaidItem {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("picture")
	@Expose
	private String picture;
	@SerializedName("percent")
	@Expose
	private Integer percent;
	@SerializedName("amount")
	@Expose
	private Integer amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
