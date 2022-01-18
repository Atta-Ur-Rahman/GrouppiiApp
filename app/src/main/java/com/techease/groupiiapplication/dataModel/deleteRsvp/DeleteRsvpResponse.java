package com.techease.groupiiapplication.dataModel.deleteRsvp;

import com.google.gson.annotations.SerializedName;

public class DeleteRsvpResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}