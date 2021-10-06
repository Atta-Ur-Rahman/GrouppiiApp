package com.techease.groupiiapplication.dataModel.getTripUser;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("phone")
	private Object phone;

	@SerializedName("latitude")
	private Object latitude;

	@SerializedName("name")
	private String name;

	@SerializedName("tripid")
	private int tripid;

	@SerializedName("userid")
	private int userid;

	@SerializedName("shared_cost")
	private int sharedCost;

	@SerializedName("picture")
	private String picture;

	@SerializedName("email")
	private String email;

	@SerializedName("longitude")
	private Object longitude;

	public void setPhone(Object phone){
		this.phone = phone;
	}

	public Object getPhone(){
		return phone;
	}

	public void setLatitude(Object latitude){
		this.latitude = latitude;
	}

	public Object getLatitude(){
		return latitude;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTripid(int tripid){
		this.tripid = tripid;
	}

	public int getTripid(){
		return tripid;
	}

	public void setUserid(int userid){
		this.userid = userid;
	}

	public int getUserid(){
		return userid;
	}

	public void setSharedCost(int sharedCost){
		this.sharedCost = sharedCost;
	}

	public int getSharedCost(){
		return sharedCost;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return picture;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLongitude(Object longitude){
		this.longitude = longitude;
	}

	public Object getLongitude(){
		return longitude;
	}
}