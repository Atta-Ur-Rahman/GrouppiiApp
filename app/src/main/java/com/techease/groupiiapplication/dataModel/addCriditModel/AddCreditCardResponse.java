package com.techease.groupiiapplication.dataModel.addCriditModel;

import com.google.gson.annotations.SerializedName;

public class AddCreditCardResponse{

	@SerializedName("data")
	private AddCreditCardDataModel data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(AddCreditCardDataModel data){
		this.data = data;
	}

	public AddCreditCardDataModel getData(){
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

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}