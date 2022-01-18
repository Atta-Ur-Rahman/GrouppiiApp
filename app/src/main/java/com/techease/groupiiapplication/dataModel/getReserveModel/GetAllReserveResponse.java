package com.techease.groupiiapplication.dataModel.getReserveModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetAllReserveResponse{

	@SerializedName("data")
	private List<GetReserveDataModel> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<GetReserveDataModel> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}