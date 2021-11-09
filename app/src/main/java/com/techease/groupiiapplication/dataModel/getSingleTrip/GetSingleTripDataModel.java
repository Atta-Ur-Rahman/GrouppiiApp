package com.techease.groupiiapplication.dataModel.getSingleTrip;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetSingleTripDataModel {

	@SerializedName("pay_date")
	private String payDate;

	@SerializedName("is_published")
	private String isPublished;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("title")
	private String title;

	@SerializedName("userid")
	private int userid;

	@SerializedName("users")
	private List<UsersItem> users;

	@SerializedName("fromdate")
	private String fromdate;

	@SerializedName("coverimage")
	private String coverimage;

	@SerializedName("todate")
	private String todate;

	@SerializedName("location")
	private String location;

	@SerializedName("id")
	private int id;

	@SerializedName("modified_at")
	private Object modifiedAt;

	@SerializedName("status")
	private String status;

	public String getPayDate(){
		return payDate;
	}

	public String getIsPublished(){
		return isPublished;
	}

	public String getDescription(){
		return description;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getTitle(){
		return title;
	}

	public int getUserid(){
		return userid;
	}

	public List<UsersItem> getUsers(){
		return users;
	}

	public String getFromdate(){
		return fromdate;
	}

	public String getCoverimage(){
		return coverimage;
	}

	public String getTodate(){
		return todate;
	}

	public String getLocation(){
		return location;
	}

	public int getId(){
		return id;
	}

	public Object getModifiedAt(){
		return modifiedAt;
	}

	public String getStatus(){
		return status;
	}
}