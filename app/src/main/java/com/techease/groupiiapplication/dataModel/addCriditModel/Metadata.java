package com.techease.groupiiapplication.dataModel.addCriditModel;

import com.google.gson.annotations.SerializedName;

public class Metadata{

	@SerializedName("token")
	private String token;

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}