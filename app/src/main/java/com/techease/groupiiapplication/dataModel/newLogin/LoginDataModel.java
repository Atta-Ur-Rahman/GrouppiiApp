package com.techease.groupiiapplication.dataModel.newLogin;

import com.google.gson.annotations.SerializedName;

public class LoginDataModel {

	@SerializedName("stripe_key")
	private Object stripeKey;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("description")
	private Object description;

	@SerializedName("usertype")
	private String usertype;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("device_type")
	private String deviceType;

	@SerializedName("type")
	private String type;

	@SerializedName("picture")
	private Object picture;

	@SerializedName("token")
	private String token;

	@SerializedName("notification")
	private int notification;

	@SerializedName("phone")
	private Object phone;

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

	public Object getStripeKey(){
		return stripeKey;
	}

	public String getLatitude(){
		return latitude;
	}

	public Object getDescription(){
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

	public String getType(){
		return type;
	}

	public Object getPicture(){
		return picture;
	}

	public String getToken(){
		return token;
	}

	public int getNotification(){
		return notification;
	}

	public Object getPhone(){
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