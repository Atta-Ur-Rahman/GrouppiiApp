package com.techease.groupiiapplication.dataModel.reserveModel;

import com.google.gson.annotations.SerializedName;

public class AddReserveResponse{

	@SerializedName("data")
	private AddReserveData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public AddReserveData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}