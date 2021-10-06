package com.techease.groupiiapplication.dataModel.getCreditCardResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Sources{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("has_more")
	private boolean hasMore;

	@SerializedName("url")
	private String url;

	@SerializedName("object")
	private String object;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setHasMore(boolean hasMore){
		this.hasMore = hasMore;
	}

	public boolean isHasMore(){
		return hasMore;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}
}