package com.techease.groupiiapplication.dataModel.newLogin;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private LoginDataModel data;

	@SerializedName("success")
	private boolean success;

	public LoginDataModel getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}
}