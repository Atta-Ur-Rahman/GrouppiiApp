package com.techease.groupiiapplication.dataModel.getPaymentExpenses;

public class GetPaymentExpensesResponse{
	private GetPaymentExpensesData data;
	private boolean success;
	private String message;

	public void setData(GetPaymentExpensesData data){
		this.data = data;
	}

	public GetPaymentExpensesData getData(){
		return data;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}
