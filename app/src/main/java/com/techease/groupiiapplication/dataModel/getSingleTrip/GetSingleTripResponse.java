package com.techease.groupiiapplication.dataModel.getSingleTrip;

import com.google.gson.annotations.SerializedName;

public class GetSingleTripResponse {

	@SerializedName("data")
	private GetSingleTripDataModel data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public GetSingleTripDataModel getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}