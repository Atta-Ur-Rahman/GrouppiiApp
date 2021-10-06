package com.techease.groupiiapplication.dataModel.updateProfile;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileDataModel {

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("usertype")
	private String usertype;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("device_type")
	private String deviceType;

	@SerializedName("picture")
	private String picture;

	@SerializedName("notification")
	private Object notification;

	@SerializedName("phone")
	private String phone;

	@SerializedName("ftoken")
	private Object ftoken;

	@SerializedName("name")
	private String name;

	@SerializedName("fcm_token")
	private String fcmToken;

	@SerializedName("id")
	private int id;

	@SerializedName("modified_at")
	private Object modifiedAt;

	@SerializedName("email")
	private String email;

	@SerializedName("longitude")
	private String longitude;

	public String getLatitude(){
		return latitude;
	}

	public String getDescription(){
		return description;
	}

	public String getUsertype(){
		return usertype;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getDeviceType(){
		return deviceType;
	}

	public String getPicture(){
		return picture;
	}

	public Object getNotification(){
		return notification;
	}

	public String getPhone(){
		return phone;
	}

	public Object getFtoken(){
		return ftoken;
	}

	public String getName(){
		return name;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public int getId(){
		return id;
	}

	public Object getModifiedAt(){
		return modifiedAt;
	}

	public String getEmail(){
		return email;
	}

	public String getLongitude(){
		return longitude;
	}
}