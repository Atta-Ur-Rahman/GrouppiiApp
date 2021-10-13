package com.techease.groupiiapplication.dataModel.loginSignup.login;

import com.google.gson.annotations.SerializedName;

public class Data{

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

	@SerializedName("picture")
	private String picture;

	@SerializedName("token")
	private String token;

	@SerializedName("notification")
	private int notification;

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

	public void setStripeKey(Object stripeKey){
		this.stripeKey = stripeKey;
	}

	public Object getStripeKey(){
		return stripeKey;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setUsertype(String usertype){
		this.usertype = usertype;
	}

	public String getUsertype(){
		return usertype;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}

	public String getDeviceType(){
		return deviceType;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return picture;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setNotification(int notification){
		this.notification = notification;
	}

	public int getNotification(){
		return notification;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setFtoken(Object ftoken){
		this.ftoken = ftoken;
	}

	public Object getFtoken(){
		return ftoken;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFcmToken(String fcmToken){
		this.fcmToken = fcmToken;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setModifiedAt(Object modifiedAt){
		this.modifiedAt = modifiedAt;
	}

	public Object getModifiedAt(){
		return modifiedAt;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}
}