package com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPaymentExpensesResponse {
	@SerializedName("success")
	@Expose
	private Boolean success;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("data")
	@Expose
	private GetPaymentExpensesData data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GetPaymentExpensesData getData() {
		return data;
	}

	public void setData(GetPaymentExpensesData data) {
		this.data = data;
	}

}
