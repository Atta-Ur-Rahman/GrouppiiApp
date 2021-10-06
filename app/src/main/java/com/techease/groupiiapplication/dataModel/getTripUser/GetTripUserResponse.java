package com.techease.groupiiapplication.dataModel.getTripUser;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;

public class GetTripUserResponse{

	@SerializedName("data")
	private List<AddTripDataModel> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<AddTripDataModel> data){
		this.data = data;
	}

	public List<AddTripDataModel> getData(){
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