package com.techease.groupiiapplication.dataModel.getCreditCardResponse;

import com.google.gson.annotations.SerializedName;

public class GetCreditCardResponse{

	@SerializedName("data")
	private GetCreditCardDataModel data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(GetCreditCardDataModel data){
		this.data = data;
	}

	public GetCreditCardDataModel getData(){
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