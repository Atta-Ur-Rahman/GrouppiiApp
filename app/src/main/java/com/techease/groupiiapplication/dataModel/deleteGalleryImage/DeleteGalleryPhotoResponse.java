package com.techease.groupiiapplication.dataModel.deleteGalleryImage;

import com.google.gson.annotations.SerializedName;

public class DeleteGalleryPhotoResponse{

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