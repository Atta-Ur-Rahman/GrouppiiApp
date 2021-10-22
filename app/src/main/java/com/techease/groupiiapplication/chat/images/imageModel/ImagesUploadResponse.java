package com.techease.groupiiapplication.chat.images.imageModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ImagesUploadResponse{

	@SerializedName("data")
	private List<String> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<String> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}