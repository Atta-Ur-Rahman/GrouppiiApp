package com.techease.groupiiapplication.dataModel.photomodel;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("file")
	private String file;

	
	@SerializedName("time")
	private String time;

	@SerializedName("title")
	private String title;

	public String getDate(){
		return date;
	}

	public String getFile(){
		return file;
	}


	public String getTime(){
		return time;
	}

	public String getTitle(){
		return title;
	}
}