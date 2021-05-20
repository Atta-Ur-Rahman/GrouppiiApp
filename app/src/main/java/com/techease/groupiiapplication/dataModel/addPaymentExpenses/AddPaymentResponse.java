package com.techease.groupiiapplication.dataModel.addPaymentExpenses;

public class AddPaymentResponse{
	private AddPaymentData data;
	private boolean success;
	private String message;

	public void setData(AddPaymentData data){
		this.data = data;
	}

	public AddPaymentData getData(){
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
