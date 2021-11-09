package com.techease.groupiiapplication.dataModel.notifications;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetNotificationsResponse{

	@SerializedName("data")
	private List<NotificationsDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<NotificationsDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}