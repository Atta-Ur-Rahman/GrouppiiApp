package com.techease.groupiiapplication.dataModel.getSingleTrip;

import com.google.gson.annotations.SerializedName;

public class UsersItem{

	@SerializedName("is_accepted")
	private Object isAccepted;

	@SerializedName("tpbid")
	private int tpbid;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("tripid")
	private int tripid;

	@SerializedName("id")
	private int id;

	@SerializedName("modified_at")
	private Object modifiedAt;

	@SerializedName("userid")
	private int userid;

	@SerializedName("picture")
	private String picture;

	@SerializedName("shared_cost")
	private int sharedCost;

	public Object getIsAccepted(){
		return isAccepted;
	}

	public int getTpbid(){
		return tpbid;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getTripid(){
		return tripid;
	}

	public int getId(){
		return id;
	}

	public Object getModifiedAt(){
		return modifiedAt;
	}

	public int getUserid(){
		return userid;
	}

	public String getPicture(){
		return picture;
	}

	public int getSharedCost(){
		return sharedCost;
	}
}