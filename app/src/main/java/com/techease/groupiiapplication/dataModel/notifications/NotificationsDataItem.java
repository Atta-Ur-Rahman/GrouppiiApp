package com.techease.groupiiapplication.dataModel.notifications;

import com.google.gson.annotations.SerializedName;

public class NotificationsDataItem {

	@SerializedName("notification")
	private String notification;

	@SerializedName("touser")
	private String touser;

	@SerializedName("noti_type")
	private String notiType;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("fromuser")
	private String fromuser;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("modified_at")
	private Object modifiedAt;

	public String getNotification(){
		return notification;
	}

	public String getTouser(){
		return touser;
	}

	public String getNotiType(){
		return notiType;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public String getFromuser(){
		return fromuser;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public Object getModifiedAt(){
		return modifiedAt;
	}
}