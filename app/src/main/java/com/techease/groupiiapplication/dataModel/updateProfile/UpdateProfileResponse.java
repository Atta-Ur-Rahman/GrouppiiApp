package com.techease.groupiiapplication.dataModel.updateProfile;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse{

	@SerializedName("data")
	private UpdateProfileDataModel data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public UpdateProfileDataModel getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}