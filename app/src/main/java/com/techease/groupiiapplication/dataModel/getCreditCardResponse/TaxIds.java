package com.techease.groupiiapplication.dataModel.getCreditCardResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TaxIds{

	@SerializedName("data")
	private List<Object> data;

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("has_more")
	private boolean hasMore;

	@SerializedName("url")
	private String url;

	@SerializedName("object")
	private String object;

	public void setData(List<Object> data){
		this.data = data;
	}

	public List<Object> getData(){
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